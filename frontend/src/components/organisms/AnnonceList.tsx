import { useEffect, useState } from "react";
import type { JobOfferResponse } from "../../types/JobOffer";
import { OffersAPI } from "../../services/api";
import OfferCard from "../molecules/AnnonceCard";


export default function OfferList() {
    const [offers, setOffers] = useState<JobOfferResponse[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        OffersAPI.getAll().then((data) => {
            setOffers(data);
            setLoading(false);
        });
    }, []);

    if (loading) {
        return <p className="text-center py-10">Chargement des offres...</p>;
    }

    if (offers.length === 0) {
        return <p className="text-center py-10">Aucune offre trouvée.</p>;
    }

    return (
        <div className="grid gap-4">
            {offers.map((offer) => (
                <OfferCard key={offer.id} offer={offer} />
            ))}
        </div>
    );
}
