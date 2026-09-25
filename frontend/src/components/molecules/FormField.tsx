import Label from "../atoms/Label";
import Input from "../atoms/Input";

interface FormFieldProps {
    label: string;
    name: string;
    type?: string;
    placeholder?: string;
    value?: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    error?: string;
    helper?: string;
}

export default function FormField({
    label,
    name,
    type = "text",
    placeholder = "",
    value,
    onChange,
    error,
    helper,
}: FormFieldProps) {
    return (
        <div className="mb-4">
            <Label htmlFor={name}>{label}</Label>

            <Input
                id={name}
                name={name}
                type={type}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                error={error}
                helper={helper}
            />
        </div>
    );
}
