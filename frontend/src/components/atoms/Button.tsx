import React from "react";
import { useNavigate } from "react-router-dom";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: "primary" | "secondary" | "danger" | "outline";
    loading?: boolean;
    to?: string; // nouvelle prop pour la navigation
}

export default function Button({
    children,
    variant = "primary",
    loading = false,
    className = "",
    disabled,
    to,
    onClick,
    ...props
}: ButtonProps) {
    const navigate = useNavigate();

    const base =
        "px-4 py-2 rounded-md font-medium transition-colors duration-200 flex items-center justify-center gap-2";

    const variants = {
        primary: "bg-blue-600 text-white hover:bg-blue-700",
        secondary: "bg-gray-200 text-gray-700 hover:bg-gray-300",
        danger: "bg-red-600 text-white hover:bg-red-700",
        outline: "border border-gray-300 text-gray-700 hover:bg-gray-100",
    };

    const isDisabled = disabled || loading;

    const handleClick: React.MouseEventHandler<HTMLButtonElement> = (e) => {
        if (isDisabled) return;

        if (to) {
            navigate(to);
            return;
        }

        if (onClick) {
            onClick(e);
        }
    };

    return (
        <button
            className={`${base} ${variants[variant]} ${isDisabled ? "opacity-50 cursor-not-allowed" : ""
                } ${className}`}
            disabled={isDisabled}
            onClick={handleClick}
            {...props}
        >
            {loading && (
                <span className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin" />
            )}
            {children}
        </button>
    );
}
