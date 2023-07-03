import { Link } from 'react-router-dom';
import { useAuth } from './security/AuthContext';
import { RestrictedFeature, Role } from './RoleComponent';
import { useEffect } from "react";
import { useLocation } from 'react-router-dom';
import { getAllNotificationsApi } from "./api/NotificationApiService";
import { HiBell } from "react-icons/hi2";
import { HiBellAlert } from "react-icons/hi2";
import ShowErrorModal from "./ErrorModalComponent";

const bellSize = 30;

export default function HeaderComponent() {

	const authContext = useAuth();
	const location = useLocation();

	function logout() {
		authContext.logout();
	}

	function getNewNotifications() {
		if (authContext.isAuthenticated) {
			getAllNotificationsApi()
				.then(response => {
					authContext.setNotifications(response.data);
				})
				.catch(error => error.response != null ? authContext.handleModalError(error.response.data) : '');
		}
	}

	function getBellType(notifications) {
		const newNotificationCount = notifications.filter(x => !x.isSeen).length;
		return newNotificationCount === 0 ?
			<span><HiBell size={bellSize} /> 0 </span> :
			<span><HiBellAlert size={bellSize} color='red' /> {newNotificationCount}</span>;
	}

	useEffect(() => getNewNotifications(), [location]);

	return (
		<header className="border-bottom border-light border-5 mb-5 p-2">
			<div className="container">
				<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
				<div className="row">
					<nav className="navbar navbar-expand-lg">
						<div className="collapse navbar-collapse">
							<ul className="navbar-nav">
								<RestrictedFeature permittedRoles={[Role.ADMIN, Role.USER]}>
									<li className="nav-item fs-5">
										{authContext.isAuthenticated && <Link className="nav-link" to={`/newticket`}>Create new ticket</Link>}
									</li>
								</RestrictedFeature>
								<RestrictedFeature permittedRoles={[Role.ADMIN, Role.USER, Role.VIEWER]}>
									<li className="nav-item fs-5">
										{authContext.isAuthenticated && <Link className="nav-link" to={`/tickets`}>Tickets</Link>}
									</li>
									<li className="nav-item fs-5">
										{authContext.isAuthenticated && getBellType(authContext.notifications)}
									</li>
								</RestrictedFeature>
							</ul>
						</div>
						<ul className="navbar-nav">
							<li className="nav-item fs-5">
								{!authContext.isAuthenticated && <Link className="nav-link" to="/login">Login</Link>}
							</li>
							<li className="nav-item fs-5">
								{authContext.isAuthenticated && <Link className="nav-link" to="/logout" onClick={logout}>Logout</Link>}
							</li>
						</ul>
					</nav>
				</div>
			</div>
		</header>
	);
}
