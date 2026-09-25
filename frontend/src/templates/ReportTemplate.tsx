import FormField from "../components/molecules/FormField";
import Button from "../components/atoms/Button";

interface ReportTemplateProps {
    url: string;
    commentaire: string;
    onUrlChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onCommentChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: () => void;
}

export default function ReportTemplate({
    url,
    commentaire,
    onUrlChange,
    onCommentChange,
    onSubmit,
}: ReportTemplateProps) {
    return (
        <div className="max-w-5xl mx-auto py-10 px-4">
            <h1 className="text-2xl font-bold mb-6">Signaler une annonce</h1>

            <FormField
                label="URL de l'annonce"
                name="url"
                value={url}
                onChange={onUrlChange}
                placeholder="https://exemple.com/annonce"
            />

            <FormField
                label="Commentaire"
                name="commentaire"
                value={commentaire}
                onChange={onCommentChange}
                placeholder="Décrivez le problème…"
            />

            <Button variant="primary" className="mt-4" onClick={onSubmit}>
                Envoyer le signalement
            </Button>
        </div>
    );
}
