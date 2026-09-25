import React from "react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
    error?: string;
    helper?: string;
}

export default function Input({
    className = "",
    error,
    helper,
    ...props
}: InputProps) {
    return (
        <div className="w-full">
            <input
                className={`w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 ${error
                        ? "border-red-500 focus:ring-red-400"
                        : "border-gray-300 focus:ring-blue-500"
                    } ${className}`}
                {...props}
            />

            {error && <p className="text-red-600 text-sm mt-1">{error}</p>}
            {helper && !error && <p className="text-gray-500 text-sm mt-1">{helper}</p>}
        </div>
    );
}
