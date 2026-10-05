import { request } from "../utils/request";


const BASE_URL = "http://localhost:8080/api/auth";

export const AuthAPI = {
    register: (email: string, password: string) =>
        request(`${BASE_URL}/register`, {
            method: "POST",
            body: JSON.stringify({ email, password }),
        }),

    login: (email: string, password: string) =>
        request(`${BASE_URL}/login`, {
            method: "POST",
            body: JSON.stringify({ email, password }),
        }),

    refresh: (refreshToken: string) =>
        request(`${BASE_URL}/refresh`, {
            method: "POST",
            body: JSON.stringify({ refreshToken }),
        }),
};
