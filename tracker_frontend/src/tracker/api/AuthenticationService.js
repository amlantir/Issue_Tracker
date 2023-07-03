import { client } from "./Client";

export const authenticateUser = (username, password) =>
    client.post('/authenticate', { username, password });