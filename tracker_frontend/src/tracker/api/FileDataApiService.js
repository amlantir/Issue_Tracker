import { client } from './Client';

export const deleteAttachmentApi = (id) => client.delete(`/filedatas/${id}`);
export const downloadAttachmentApi = (id) => client.get(`/filedatas/download/${id}`, {responseType: 'blob'});
export const uploadAttachmentApi = (ticketId, attachment) => client.post(`/filedatas/${ticketId}`, attachment);