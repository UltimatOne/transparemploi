import React from "react";

interface LabelProps extends React.LabelHTMLAttributes<HTMLLabelElement> { }

export default function Label({ children, className = "", ...props }: LabelProps) {
    return (
        <label
            className={`block mb-1 font-medium text-gray-700 ${className}`}
            {...props}
        >
            {children}
        </label>
    );
}
