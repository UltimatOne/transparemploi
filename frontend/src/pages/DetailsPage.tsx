import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import DetailsTemplate from "../templates/DetailsTemplate";
import { OffersAPI } from "../services/api";
import type { JobOfferResponse } from "../types/JobOffer";


export default function OfferDetailsPage() {
    const { id } = useParams();
    const [offer, setOffer] = useState<JobOfferResponse | null>(null);

    useEffect(() => {
        if (!id) return;

        // TEMPORAIRE : en attendant GET /api/offers/{id}
        OffersAPI.getAll().then((data) => {
            const found = data.find((o) => o.id === Number(id));
            setOffer(found || null);
        });
    }, [id]);

    return <DetailsTemplate offer={offer} />;
}
