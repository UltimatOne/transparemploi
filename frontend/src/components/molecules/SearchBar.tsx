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
        <div className="flex gap-3">
            <Input
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className="flex-1"
            />

            <Button onClick={onSearch} variant="primary">
                Rechercher
            </Button>
        </div>
    );
}
