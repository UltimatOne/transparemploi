import { useState } from "react";
import ReportTemplate from "../templates/ReportTemplate";
import { OffersAPI } from "../services/api";

export default function OfferReportPage() {
    const [url, setUrl] = useState("");
    const [commentaire, setCommentaire] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);

    const handleSubmit = async () => {
        if (!url.trim()) return;

        setIsSubmitting(true);
        setSuccess(false);
        setError(false);

        try {
            await OffersAPI.report({ url, commentaire }); // 🔹 POST /api/offers/report
            setSuccess(true);
            setUrl("");
            setCommentaire("");
        } catch {
            setError(true);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <ReportTemplate
            url={url}
            commentaire={commentaire}
            onUrlChange={(e) => setUrl(e.target.value)}
            onCommentChange={(e) => setCommentaire(e.target.value)}
            onSubmit={handleSubmit}
            isSubmitting={isSubmitting}
            success={success}
            error={error}
        />
    );
}
