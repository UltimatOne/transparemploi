import React from "react";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: "primary" | "secondary" | "danger" | "outline";
    loading?: boolean;
}

export default function Button({
    children,
    variant = "primary",
    loading = false,
    className = "",
    disabled,
    ...props
}: ButtonProps) {
    const base =
        "px-4 py-2 rounded-md font-medium transition-colors duration-200 flex items-center justify-center gap-2";

    const variants = {
        primary: "bg-blue-600 text-white hover:bg-blue-700",
        secondary: "bg-gray-200 text-gray-700 hover:bg-gray-300",
        danger: "bg-red-600 text-white hover:bg-red-700",
        outline: "border border-gray-300 text-gray-700 hover:bg-gray-100",
    };

    return (
        <button
            className={`${base} ${variants[variant]} ${disabled || loading ? "opacity-50 cursor-not-allowed" : ""
                } ${className}`}
            disabled={disabled || loading}
            {...props}
        >
            {loading && (
                <span className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin" />
            )}
            {children}
        </button>
    );
}
