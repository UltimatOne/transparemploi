import React from "react";

interface CardProps {
    children: React.ReactNode;
    header?: React.ReactNode;
    footer?: React.ReactNode;
    className?: string;
}

export default function Card({ children, header, footer, className = "" }: CardProps) {
    return (
        <div
            className={`p-4 bg-white border border-gray-200 rounded-lg shadow-sm ${className}`}
        >
            {header && <div className="mb-3">{header}</div>}
            {children}
            {footer && <div className="mt-3">{footer}</div>}
        </div>
    );
}
