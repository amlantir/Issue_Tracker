import { client } from './Client';

export const createTicketApi = (body) => client.post(`/tickets`, body);

export const getAllTicketsByNameApi = () => client.get(`/tickets/name`);

export const getAllTicketsApi = () => client.get(`/tickets`);

export const getTicketApi = (id) => client.get(`/tickets/id/${id}`);

export const updateTicketDescriptionApi = (id, description) => client.post(`/tickets/description/id/${id}`, description);

export const updateTicketIssueNameApi = (id, issueName) => client.post(`/tickets/issue-name/id/${id}`, issueName);

export const updateTicketAssigneesApi = (id, assignees) => client.post(`/tickets/assignees/id/${id}`, assignees);

export const updateTicketStatusApi = (id, status) => client.post(`/tickets/status/id/${id}`, status);

export const updateTicketTypeApi = (id, type) => client.post(`/tickets/type/id/${id}`, type);

export const updateTicketPriorityApi = (id, priority) => client.post(`/tickets/priority/id/${id}`, priority);

export const fillCreateTicketData = () => client.get(`/tickets/fill-create-ticket-data`);

export const deleteTicketApi = (id, isAllTickets) => client.delete(`/tickets/id/${id}/${isAllTickets}`);