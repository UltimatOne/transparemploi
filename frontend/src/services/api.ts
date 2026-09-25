import type { Annonce } from "../types/annonce";

const API_URL = "http://localhost:8080/api"; // Spring Boot

// -----------------------------
// GET : toutes les annonces
// -----------------------------
export async function getAnnonces(): Promise<Annonce[]> {
    try {
        const response = await fetch(`${API_URL}/annonces`);
        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des annonces");
        }
        return await response.json();
    } catch (error) {
        console.error("API getAnnonces :", error);
        return [];
    }
}

// -----------------------------
// GET : annonce par ID
// -----------------------------
export async function getAnnonceById(id: string): Promise<Annonce | null> {
    try {
        const response = await fetch(`${API_URL}/annonces/${id}`);
        if (!response.ok) {
            throw new Error("Erreur lors de la récupération de l'annonce");
        }
        return await response.json();
    } catch (error) {
        console.error("API getAnnonceById :", error);
        return null;
    }
}

// -----------------------------
// POST : signaler une annonce
// -----------------------------
export async function createAnnonce(data: {
    url: string;
    commentaire: string;
}): Promise<boolean> {
    try {
        const response = await fetch(`${API_URL}/annonces`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error("Erreur lors de l'envoi du signalement");
        }

        return true;
    } catch (error) {
        console.error("API createAnnonce :", error);
        return false;
    }
}
