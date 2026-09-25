import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import DetailsTemplate from "../templates/DetailsTemplate";
import { getAnnonceById } from "../services/api";
import type { Annonce } from "../types/annonce";

export default function DetailsPage() {
    const { id } = useParams();
    const [annonce, setAnnonce] = useState<Annonce | null>(null);

    useEffect(() => {
        if (id) {
            getAnnonceById(id).then((data) => setAnnonce(data));
        }
    }, [id]);

    return <DetailsTemplate annonce={annonce} />;
}
