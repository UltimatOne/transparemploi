import SearchBar from "../components/molecules/SearchBar";
import AnnonceList from "../components/organisms/AnnonceList";

interface HomeTemplateProps {
    searchValue: string;
    onSearchChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSearch: () => void;
}

export default function HomeTemplate({
    searchValue,
    onSearchChange,
    onSearch,
}: HomeTemplateProps) {
    return (
        <div className="max-w-5xl mx-auto py-10 px-4">
            <h1 className="text-2xl font-bold mb-6">Rechercher une annonce</h1>

            <SearchBar
                value={searchValue}
                onChange={onSearchChange}
                onSearch={onSearch}
            />

            <div className="mt-10">
                <AnnonceList />
            </div>
        </div>
    );
}
