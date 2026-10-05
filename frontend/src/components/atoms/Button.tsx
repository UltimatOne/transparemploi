import React from "react";
import { useNavigate } from "react-router-dom";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: "primary" | "secondary" | "danger" | "outline";
    isLoading?: boolean;
    to?: string;
}

export default function Button({
    children,
    variant = "primary",
    isLoading = false,
    className = "",
    disabled,
    to,
    onClick,
    ...props
}: ButtonProps) {
    const navigate = useNavigate();

    const base = `
        px-4 py-2 rounded-md font-medium
        flex items-center justify-center gap-2
        transition-all duration-200
        focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
        active:scale-[0.97]
    `;

    const variants = {
        primary: `
            bg-blue-600 text-white
            hover:bg-blue-700
            active:bg-blue-800
        `,
        secondary: `
            bg-gray-200 text-gray-700
            hover:bg-gray-300
            active:bg-gray-400
        `,
        danger: `
            bg-red-600 text-white
            hover:bg-red-700
            active:bg-red-800
        `,
        outline: `
            border border-gray-300 text-gray-700
            hover:bg-gray-100
            active:bg-gray-200
        `,
    };

    const isDisabled = disabled || isLoading;

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
            className={`
                ${base}
                ${variants[variant]}
                ${isDisabled ? "opacity-50 cursor-not-allowed" : ""}
                ${className}
            `}
            disabled={isDisabled}
            onClick={handleClick}
            {...props}
        >
            {isLoading && (
                <span className="
                    w-4 h-4 border-2 border-white
                    border-t-transparent rounded-full
                    animate-spin
                " />
            )}
            {!isLoading && children}
        </button>
    );
}
