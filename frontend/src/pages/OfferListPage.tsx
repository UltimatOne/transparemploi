import { useEffect, useState } from "react";
import ListTemplate from "../templates/ListTemplate";
import { OffersAPI } from "../services/api";
import type { JobOfferResponse } from "../types/JobOffer";

export default function OfferListPage() {
    const [offers, setOffers] = useState<JobOfferResponse[]>([]);
    const [query, setQuery] = useState("");

    useEffect(() => {
        const fetchOffers = async () => {
            try {
                const data = query
                    ? await OffersAPI.search(query) // GET /api/offers/search?query=...
                    : await OffersAPI.getAll();     // GET /api/offers
                setOffers(data);
            } catch {
                setOffers([]);
            }
        };

        fetchOffers();
    }, [query]);

    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setQuery(e.target.value);
    };

    const handleSearch = () => {
        // Ici, on déclenche juste la recherche via useEffect (query déjà mis à jour)
        // Tu peux ajouter de la logique si besoin (debounce, etc.)
    };

    return (
        <ListTemplate
            offers={offers}
            searchValue={query}
            onSearchChange={handleSearchChange}
            onSearch={handleSearch}
        />
    );
}
