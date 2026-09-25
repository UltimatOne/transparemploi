import OfferCard from "../components/molecules/AnnonceCard";
import type { JobOfferResponse } from "../types/JobOffer";


interface DetailsTemplateProps {
    offer: JobOfferResponse | null;
}

export default function OfferDetailsTemplate({ offer }: DetailsTemplateProps) {
    if (!offer) {
        return (
            <p className="text-center py-10 text-gray-600">
                Aucune offre trouvée.
            </p>
        );
    }

    return (
        <div className="max-w-5xl mx-auto py-10 px-4">
            <h1 className="text-2xl font-bold mb-6">Détails de l'offre</h1>

            <OfferCard offer={offer} />
        </div>
    );
}
