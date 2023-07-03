import { client } from './Client';

export const getAllNotificationsApi = () => client.get(`/notifications`);