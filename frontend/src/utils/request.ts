import { AuthAPI } from "../services/AuthAPI";

export async function request(
    url: string,
    options: RequestInit = {}
): Promise<any> {
    const token = localStorage.getItem("token");
    const refreshToken = localStorage.getItem("refresh");

    const headers = {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
        ...options.headers,
    };

    let response = await fetch(url, { ...options, headers });

    // Token expiré → 401 → on tente un refresh
    if (response.status === 401 && refreshToken) {
        try {
            const refreshResponse = await AuthAPI.refresh(refreshToken);

            // Nouveau token + refresh
            localStorage.setItem("token", refreshResponse.token);
            localStorage.setItem("refresh", refreshResponse.refreshToken);

            // On rejoue la requête initiale avec le nouveau token
            const retryHeaders = {
                ...headers,
                Authorization: `Bearer ${refreshResponse.token}`,
            };

            response = await fetch(url, { ...options, headers: retryHeaders });
        } catch (err) {
            // Refresh impossible → session expirée → déconnexion
            localStorage.removeItem("token");
            localStorage.removeItem("refresh");
            throw new Error("Session expirée. Veuillez vous reconnecter.");
        }
    }

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(
            `Erreur HTTP ${response.status}: ${errorText || response.statusText}`
        );
    }

    // Réponse vide (204 No Content)
    if (response.status === 204) return null;

    return response.json();
}
