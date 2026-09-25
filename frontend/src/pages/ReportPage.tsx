import { useState } from "react";
import ReportTemplate from "../templates/ReportTemplate";

export default function ReportPage() {
    const [url, setUrl] = useState("");
    const [commentaire, setCommentaire] = useState("");

    const handleSubmit = () => {
        console.log("Signalement envoyé :", { url, commentaire });
        // Tu pourras appeler ton backend ici
    };

    return (
        <ReportTemplate
            url={url}
            commentaire={commentaire}
            onUrlChange={(e) => setUrl(e.target.value)}
            onCommentChange={(e) => setCommentaire(e.target.value)}
            onSubmit={handleSubmit}
        />
    );
}
