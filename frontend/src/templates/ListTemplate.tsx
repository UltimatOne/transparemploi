import SearchBar from "../components/molecules/SearchBar";
import AnnonceList from "../components/organisms/AnnonceList";
import type { JobOfferResponse } from "../types/JobOffer";

interface ListTemplateProps {
    offers: JobOfferResponse[];
    searchValue: string;
    onSearchChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSearch: () => void;
}

export default function ListTemplate({
    offers,
    searchValue,
    onSearchChange,
    onSearch,
}: ListTemplateProps) {
    return (
        <section
            aria-labelledby="offers-title"
            className="max-w-5xl mx-auto py-10 px-4 sm:px-6 lg:px-8"
        >
            <h1
                id="offers-title"
                className="text-3xl font-bold mb-8 text-gray-900"
            >
                Toutes les annonces
            </h1>

            <div className="mb-8">
                <SearchBar
                    value={searchValue}
                    onChange={onSearchChange}
                    onSearch={onSearch}
                    placeholder="Rechercher une annonce..."
                />
            </div>

            <AnnonceList offers={offers} />
        </section>
    );
}
