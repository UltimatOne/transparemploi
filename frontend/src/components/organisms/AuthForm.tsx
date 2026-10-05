import AuthField from "../molecules/AuthField";
import Button from "../atoms/Button";
import type { RefObject } from "react";

interface FieldConfig {
    id: string;
    label: string;
    type?: string;
    value: string;
    required?: boolean;
    error?: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    ref?: RefObject<HTMLInputElement>; // focus automatique
}

interface AuthFormProps {
    isLoading: boolean;
    title: string;
    fields: FieldConfig[];
    submitLabel?: string;
    onSubmit: () => void;
}

export default function AuthForm({
    isLoading,
    title,
    fields,
    submitLabel = "Valider",
    onSubmit,
}: AuthFormProps) {
    return (
        <div className="w-full">
            <h2
                id="auth-modal-title"
                className="
                    text-2xl sm:text-3xl
                    font-bold
                    mb-6
                    text-center
                "
            >
                {title}
            </h2>

            <form
                aria-labelledby="auth-modal-title"
                className="flex flex-col gap-4"
                onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit();
                }}
            >
                {fields.map((field) => {
                    const describedBy = field.error
                        ? `${field.id}-error`
                        : undefined;

                    return (
                        <div key={field.id}>
                            <AuthField
                                {...field}
                                aria-invalid={!!field.error}
                                aria-describedby={describedBy}
                            />

                            {/* Message d’erreur inline accessible */}
                            {field.error && (
                                <p
                                    id={`${field.id}-error`}
                                    className="text-red-600 text-sm mt-1"
                                    role="alert"
                                >
                                    {field.error}
                                </p>
                            )}
                        </div>
                    );
                })}

                <Button
                    isLoading={isLoading}
                    variant="primary"
                    className="w-full mt-2"
                    type="submit"
                    aria-label={`Bouton pour ${submitLabel.toLowerCase()}`}
                >
                    {submitLabel}
                </Button>
            </form>
        </div>
    );
}
