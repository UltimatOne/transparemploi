import OfferCard from "../components/molecules/AnnonceCard";
import type { JobOfferResponse } from "../types/JobOffer";

interface DetailsTemplateProps {
    offer: JobOfferResponse | null;
}

export default function OfferDetailsTemplate({ offer }: DetailsTemplateProps) {
    if (!offer) {
        return (
            <p
                role="status"
                className="text-center py-10 text-gray-600 text-lg"
            >
                Aucune offre trouvée.
            </p>
        );
    }

    return (
        <section
            aria-labelledby="offer-title"
            className="max-w-5xl mx-auto py-10 px-4 sm:px-6 lg:px-8"
        >
            <h1
                id="offer-title"
                className="text-3xl font-bold mb-8 text-gray-900"
            >
                Détails de l'offre
            </h1>

            <div className="w-full">
                <OfferCard offer={offer} />
            </div>

            <article className="mt-10 bg-white shadow-sm rounded-lg p-6">
                <h2 className="text-xl font-semibold mb-4 text-gray-800">
                    Description du poste
                </h2>

                <p className="text-gray-700 leading-relaxed whitespace-pre-line">
                    {offer.description}
                </p>
            </article>
        </section>
    );
}
