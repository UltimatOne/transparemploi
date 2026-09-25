import { useEffect, useState } from "react";
import { getAnnonces } from "../../services/api";
import type { Annonce } from "../../types/annonce";
import AnnonceCard from "../molecules/AnnonceCard";

export default function AnnonceList() {
    const [annonces, setAnnonces] = useState<Annonce[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getAnnonces().then((data) => {
            setAnnonces(data);
            setLoading(false);
        });
    }, []);

    if (loading) {
        return <p className="text-center py-10">Chargement des annonces...</p>;
    }

    if (annonces.length === 0) {
        return <p className="text-center py-10">Aucune annonce trouvée.</p>;
    }

    return (
        <div className="grid gap-4">
            {annonces.map((annonce) => (
                <AnnonceCard key={annonce.id} annonce={annonce} />
            ))}
        </div>
    );
}
