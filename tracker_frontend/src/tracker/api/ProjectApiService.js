import { client } from './Client';

export const getAllProjectsApi = (ticket) => client.get(`/projects`, ticket);