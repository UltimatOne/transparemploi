import FormField from "../components/molecules/FormField";
import Button from "../components/atoms/Button";

interface ReportTemplateProps {
    url: string;
    commentaire: string;
    onUrlChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onCommentChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: () => void;
    isSubmitting?: boolean;
    success?: boolean;
    error?: boolean;
}

export default function ReportTemplate({
    url,
    commentaire,
    onUrlChange,
    onCommentChange,
    onSubmit,
    isSubmitting = false,
    success = false,
    error = false,
}: ReportTemplateProps) {
    return (
        <section
            aria-labelledby="report-title"
            className="max-w-5xl mx-auto py-10 px-4 sm:px-6 lg:px-8"
        >
            <h1
                id="report-title"
                className="text-3xl font-bold mb-8 text-gray-900"
            >
                Signaler une annonce
            </h1>

            {/* Champ URL */}
            <FormField
                label="URL de l'annonce"
                name="url"
                value={url}
                onChange={onUrlChange}
                placeholder="https://exemple.com/annonce"
            />

            {/* Champ commentaire */}
            <FormField
                label="Commentaire"
                name="commentaire"
                value={commentaire}
                onChange={onCommentChange}
                placeholder="Décrivez le problème…"
            />

            {/* Bouton d'envoi */}
            <Button
                variant="primary"
                className="mt-6"
                onClick={onSubmit}
                disabled={isSubmitting}
            >
                {isSubmitting ? "Envoi en cours…" : "Envoyer le signalement"}
            </Button>

            {/* Messages de feedback */}
            {success && (
                <p
                    role="status"
                    className="mt-4 text-green-600 font-medium"
                >
                    Signalement envoyé avec succès.
                </p>
            )}

            {error && (
                <p
                    role="alert"
                    className="mt-4 text-red-600 font-medium"
                >
                    Une erreur est survenue. Veuillez réessayer.
                </p>
            )}
        </section>
    );
}
