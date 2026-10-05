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
    required?: boolean;
    className?: string;
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
    required = false,
    className = "",
}: FormFieldProps) {
    return (
        <div className={`mb-4 w-full ${className}`}>
            <Label htmlFor={name} required={required}>
                {label}
            </Label>

            <Input
                id={name}
                name={name}
                type={type}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                error={error}
                aria-describedby={helper ? `${name}-helper` : undefined}
                aria-invalid={!!error}
            />

            {helper && !error && (
                <p
                    id={`${name}-helper`}
                    className="mt-1 text-sm text-gray-500"
                >
                    {helper}
                </p>
            )}

            {error && (
                <p
                    role="alert"
                    className="mt-1 text-sm text-red-600 font-medium"
                >
                    {error}
                </p>
            )}
        </div>
    );
}
