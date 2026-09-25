import type { Annonce } from "../types/annonce";
import AnnonceCard from "../components/molecules/AnnonceCard";

interface DetailsTemplateProps {
    annonce: Annonce | null;
}

export default function DetailsTemplate({ annonce }: DetailsTemplateProps) {
    if (!annonce) {
        return (
            <p className="text-center py-10 text-gray-600">
                Aucune annonce trouvée.
            </p>
        );
    }

    return (
        <div className="max-w-5xl mx-auto py-10 px-4">
            <h1 className="text-2xl font-bold mb-6">Détails de l'annonce</h1>

            <AnnonceCard annonce={annonce} />
        </div>
    );
}
