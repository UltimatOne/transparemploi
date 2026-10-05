import React from "react";

interface LabelProps extends React.LabelHTMLAttributes<HTMLLabelElement> {
    htmlFor: string;
    required?: boolean;
}

export default function Label({
    children,
    htmlFor,
    required = false,
    className = "",
    ...props
}: LabelProps) {
    return (
        <label
            htmlFor={htmlFor}
            className={`
        block
        mb-1
        font-medium
        text-gray-700
        text-sm sm:text-base
        ${className}
      `}
            {...props}
        >
            {children}
            {required && (
                <span className="text-red-600 ml-1" aria-hidden="true">
                    *
                </span>
            )}
        </label>
    );
}
