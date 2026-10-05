import React from "react";

interface CardProps {
    header?: React.ReactNode;
    footer?: React.ReactNode;
    children: React.ReactNode;
    className?: string;
    role?: string; // optionnel pour accessibilité avancée
}

export default function Card({
    header,
    footer,
    children,
    className = "",
    role = "region",
}: CardProps) {
    return (
        <section
            role={role}
            className={`
        bg-white
        border border-gray-200
        rounded-lg
        shadow-sm
        p-4 sm:p-6
        w-full
        ${className}
      `}
        >
            {/* Header */}
            {header && (
                <header
                    className="mb-4 text-lg sm:text-xl font-semibold text-gray-900"
                >
                    {header}
                </header>
            )}

            {/* Body */}
            <div className="text-gray-700 text-sm sm:text-base leading-relaxed">
                {children}
            </div>

            {/* Footer */}
            {footer && (
                <footer className="mt-4 pt-4 border-t border-gray-200">
                    {footer}
                </footer>
            )}
        </section>
    );
}
