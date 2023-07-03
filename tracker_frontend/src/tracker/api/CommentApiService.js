import { client } from './Client';

export const createCommentApi = (body) => client.post(`/comments`, body);

export const updateCommentApi = (id, comment) => client.post(`/comments/${id}`, comment);

export const deleteCommentApi = (id) => client.delete(`/comments/${id}`);