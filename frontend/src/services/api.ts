// src/api/api.ts

import type { JobOfferResponse } from "../types/JobOffer";
import type { UserResponse } from "../types/User";

// -----------------------------
// CONFIG
// -----------------------------
const BASE_URL = "http://localhost:8080/api";

// Helper générique pour toutes les requêtes
async function request(url: string, options: RequestInit = {}) {
    const res = await fetch(url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        },
        credentials: "include", // pour JWT + refresh token
    });

    if (!res.ok) {
        throw new Error(`API error: ${res.status}`);
    }

    const contentType = res.headers.get("Content-Type") || "";
    return contentType.includes("application/json") ? res.json() : res.text();
}

// -----------------------------
// AUTH CONTROLLER (/api/auth)
// -----------------------------
export const AuthAPI = {
    register: (data: any) =>
        request(`${BASE_URL}/auth/register`, {
            method: "POST",
            body: JSON.stringify(data),
        }),

    login: (data: any) =>
        request(`${BASE_URL}/auth/login`, {
            method: "POST",
            body: JSON.stringify(data),
        }),

    logout: () =>
        request(`${BASE_URL}/auth/logout`, {
            method: "POST",
        }),

    me: () =>
        request(`${BASE_URL}/auth/me`, {
            method: "GET",
        }),

    refresh: (refreshToken: string) =>
        request(`${BASE_URL}/auth/refresh`, {
            method: "POST",
            body: JSON.stringify({ refreshToken }),
        }),
};

// -----------------------------
// JOB OFFER CONTROLLER (/api/offers)
// -----------------------------
export const OffersAPI = {
    getAll: (): Promise<JobOfferResponse[]> =>
        request(`${BASE_URL}/offers`, {
            method: "GET",
        }),

    create: (data: {
        title: string;
        company: string;
        location: string;
        description: string;
        transparent: boolean;
    }): Promise<JobOfferResponse> =>
        request(`${BASE_URL}/offers`, {
            method: "POST",
            body: JSON.stringify(data),
        }),

    getTransparent: (): Promise<JobOfferResponse[]> =>
        request(`${BASE_URL}/offers/transparent`, {
            method: "GET",
        }),
};

// -----------------------------
// USER CONTROLLER (/api/users)
// -----------------------------
export const UsersAPI = {
    getAll: (): Promise<UserResponse[]> =>
        request(`${BASE_URL}/users`, {
            method: "GET",
        }),

    getById: (id: number): Promise<UserResponse> =>
        request(`${BASE_URL}/users/${id}`, {
            method: "GET",
        }),

    update: (id: number, data: any): Promise<UserResponse> =>
        request(`${BASE_URL}/users/${id}`, {
            method: "PUT",
            body: JSON.stringify(data),
        }),

    updateRole: (id: number, role: string): Promise<UserResponse> =>
        request(`${BASE_URL}/users/${id}/role`, {
            method: "PUT",
            body: JSON.stringify({ role }),
        }),

    delete: (id: number): Promise<UserResponse> =>
        request(`${BASE_URL}/users/${id}`, {
            method: "DELETE",
        }),
};
