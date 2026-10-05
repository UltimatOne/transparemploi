import AnnonceCard from "../molecules/AnnonceCard";
import type { JobOfferResponse } from "../../types/JobOffer";

interface AnnonceListProps {
    offers: JobOfferResponse[];
}

export default function AnnonceList({ offers }: AnnonceListProps) {
    if (!offers || offers.length === 0) {
        return (
            <p
                role="status"
                className="text-center py-10 text-gray-700 text-sm sm:text-base"
            >
                Aucune annonce trouvée.
            </p>
        );
    }

    return (
        <ul
            aria-label="Liste des annonces"
            className="
                grid
                gap-4
                sm:grid-cols-2
                lg:grid-cols-3
            "
        >
            {offers.map((offer) => (
                <li key={offer.id}>
                    <AnnonceCard offer={offer} />
                </li>
            ))}
        </ul>
    );
}
