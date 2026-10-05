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

        OffersAPI.getById(Number(id))
            .then((data) => setOffer(data))
            .catch(() => setOffer(null));
    }, [id]);

    return <DetailsTemplate offer={offer} />;
}
