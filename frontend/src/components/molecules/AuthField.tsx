import React from "react";
import Label from "../atoms/Label";
import Input from "../atoms/Input";

interface AuthFieldProps {
    id: string;
    label: string;
    type?: string;
    value: string;
    required?: boolean;
    error?: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function AuthField({
    id,
    label,
    type = "text",
    value,
    required = false,
    error,
    onChange,
}: AuthFieldProps) {
    return (
        <div
            className={`
                flex flex-col w-full mb-4
                transition-all duration-200
                ${error ? "animate-shake" : ""}
            `}
        >
            <Label htmlFor={id} required={required}>
                {label}
            </Label>

            <Input
                id={id}
                type={type}
                value={value}
                onChange={onChange}
                error={error}
                required={required}
                className={`
                    transition-all duration-200
                    focus:ring-2 focus:ring-blue-500 focus:ring-offset-1
                    hover:border-blue-400
                `}
            />
        </div>
    );
}
