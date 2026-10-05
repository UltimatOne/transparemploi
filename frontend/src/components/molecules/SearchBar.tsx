import Input from "../atoms/Input";
import Button from "../atoms/Button";

interface SearchBarProps {
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSearch: () => void;
    placeholder?: string;
}

export default function SearchBar({
    value,
    onChange,
    onSearch,
    placeholder = "Rechercher...",
}: SearchBarProps) {
    return (
        <form
            role="search"
            aria-label="Recherche d'annonces"
            className="flex flex-col sm:flex-row gap-3 w-full"
            onSubmit={(e) => {
                e.preventDefault();
                onSearch();
            }}
        >
            <Input
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className="flex-1"
            />

            <Button
                type="submit"
                variant="primary"
                className="w-full sm:w-auto"
            >
                Rechercher
            </Button>
        </form>
    );
}
