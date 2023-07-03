import { useAuth } from './security/AuthContext';

export const RestrictedFeature = ({ permittedRoles, children }) => {

	const backendRoles = useAuth().roles;

	const userRoles = (Array.isArray(backendRoles) && backendRoles.length) ? backendRoles : [];

	const hasAccess = permittedRoles.some(v => userRoles.includes(v));

	if (hasAccess) {
		return children;
	}

	return null;
}

export const Role = Object.freeze({
	ADMIN: 'ROLE_ADMIN',
	USER: 'ROLE_USER',
	VIEWER: 'ROLE_VIEWER'
});