import { useEffect, useRef, useState } from "react";
import AuthForm from "./AuthForm";

interface AuthModalProps {
    isOpen: boolean;
    isLoading: boolean;
    onClose: () => void;
    errorMessage?: string;
    successMessage?: string;
    fields: any[];
    onSubmit: () => void;
}

export default function AuthModal({
    isOpen,
    isLoading,
    onClose,
    errorMessage,
    successMessage,
    fields,
    onSubmit,
}: AuthModalProps) {
    const modalRef = useRef<HTMLDivElement>(null);
    const firstInputRef = useRef<HTMLInputElement>(null);
    const closeButtonRef = useRef<HTMLButtonElement>(null);

    const [isClosing, setIsClosing] = useState(false);

    // Gestion ouverture / fermeture animée
    useEffect(() => {
        if (!isOpen) {
            setIsClosing(true);
            const timer = setTimeout(() => {
                setIsClosing(false);
            }, 250); // durée animation fermeture
            return () => clearTimeout(timer);
        }
    }, [isOpen]);

    // Fermeture avec la touche ESC
    useEffect(() => {
        const handleKeyDown = (e: KeyboardEvent) => {
            if (e.key === "Escape") {
                setIsClosing(true);
                setTimeout(() => onClose(), 250);
            }
        };
        document.addEventListener("keydown", handleKeyDown);
        return () => document.removeEventListener("keydown", handleKeyDown);
    }, [onClose]);

    // Focus trap + focus sur le premier champ
    useEffect(() => {
        if (isOpen && modalRef.current) {
            // Focus sur la modale
            modalRef.current?.focus();

            // Focus sur le premier champ du formulaire
            setTimeout(() => {
                firstInputRef.current?.focus();
            }, 50);
        }
    }, [isOpen]);

    if (!isOpen && !isClosing) return null;

    return (
        <div
            className="
                fixed inset-0 bg-black bg-opacity-50
                flex items-center justify-center
                z-50
                p-4
            "
            role="dialog"
            aria-modal="true"
            aria-labelledby="auth-modal-title"
        >
            <div
                ref={modalRef}
                tabIndex={-1}
                className="
                    bg-white
                    w-full max-w-md
                    rounded-lg
                    shadow-lg
                    p-6
                    relative
                    outline-none
                    transform
                    transition-all
                    duration-300
                    scale-95
                    opacity-0
                    animate-modal-in
                "
            >
                {/* Bouton fermer */}
                <button
                    ref={closeButtonRef}
                    onClick={() => {
                        setIsClosing(true);
                        setTimeout(() => onClose(), 250);
                        closeButtonRef.current?.focus();
                    }}
                    className="
                        absolute top-3 right-3
                        text-gray-600 hover:text-gray-800
                        text-2xl font-bold
                        focus:outline-none
                        focus:ring-2
                        focus:ring-blue-500
                        rounded
                    "
                    aria-label="Fermer la fenêtre de connexion"
                >
                    ×
                </button>

                {/* Zone aria-live pour les messages */}
                <div aria-live="assertive">
                    {errorMessage && (
                        <div
                            className="
                                mb-4
                                text-red-700
                                bg-red-100
                                border border-red-300
                                p-3
                                rounded
                                text-sm
                            "
                            role="alert"
                        >
                            {errorMessage}
                        </div>
                    )}

                    {successMessage && (
                        <div
                            className="
                                mb-4
                                text-green-700
                                bg-green-100
                                border border-green-300
                                p-3
                                rounded
                                text-sm
                            "
                            role="status"
                        >
                            {successMessage}
                        </div>
                    )}
                </div>

                <AuthForm
                    isLoading={isLoading}
                    title="Connexion"
                    submitLabel="Se connecter"
                    fields={fields.map((field, index) => ({
                        ...field,
                        ref: index === 0 ? firstInputRef : undefined,
                    }))}
                    onSubmit={onSubmit}
                />
            </div>
        </div>
    );
}
