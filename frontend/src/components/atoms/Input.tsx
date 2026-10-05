import React from "react";

interface InputProps
    extends React.InputHTMLAttributes<HTMLInputElement> {
    label?: string;
    error?: string;
}

export default function Input({
    label,
    error,
    className = "",
    id,
    ...props
}: InputProps) {
    const inputId = id || `input-${Math.random().toString(36).slice(2)}`;

    return (
        <div className="flex flex-col w-full">
            {label && (
                <label
                    htmlFor={inputId}
                    className="mb-1 font-medium text-gray-700 text-sm sm:text-base"
                >
                    {label}
                </label>
            )}

            <input
                id={inputId}
                aria-label={label || props.placeholder}
                aria-invalid={!!error}
                className={`
          w-full
          px-3 sm:px-4
          py-2 sm:py-3
          rounded-md
          border
          text-sm sm:text-base
          focus:outline-none
          focus:ring-2
          focus:ring-blue-500
          focus:ring-offset-1
          transition
          ${error ? "border-red-500" : "border-gray-300"}
          ${className}
        `}
                {...props}
            />

            {error && (
                <span
                    role="alert"
                    className="mt-1 text-sm text-red-600"
                >
                    {error}
                </span>
            )}
        </div>
    );
}
