import { createContext, useContext, useState } from "react";
import { client } from "../api/Client";
import { authenticateUser } from "../api/AuthenticationService";

export const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({ children }) {

	const [isAuthenticated, setAuthenticated] = useState(false);

	const [username, setUsername] = useState(null);

	const [token, setToken] = useState(null);

	const [roles, setRoles] = useState(null);

	const [notifications, setNotifications] = useState([]);

	const [modalShow, setModalShow] = useState(false);

	const [modalErrorMessage, setModalErrorMessage] = useState('');

	function handleModalError(error) {
		setModalShow(true);
		setModalErrorMessage(error);
	}

	async function login(username, password) {

		try {
			const response = await authenticateUser(username, password);

			if (response.status === 200) {
				const token = 'Bearer ' + response.data.token;
				const roles = response.data.roles;

				setAuthenticated(true);
				setUsername(username);
				setToken(token);
				setRoles(roles);

				client.interceptors.request.use(
					(config) => {
						config.headers.Authorization = token;
						return config;
					}
				)

				return true;
			} else {
				logout();
				return false;
			}
		} catch (error) {
			logout();
			return false;
		}
	}

	function logout() {
		setAuthenticated(false);
		setUsername(null);
		setToken(null);
		setRoles(null);
	}

	return (
		<AuthContext.Provider value={{ isAuthenticated, login, logout, username, token, roles, notifications, setNotifications, handleModalError, modalShow, setModalShow, modalErrorMessage }}>
			{children}
		</AuthContext.Provider>
	);
}