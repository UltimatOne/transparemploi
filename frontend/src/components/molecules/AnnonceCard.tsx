import Card from "../atoms/Card";
import Button from "../atoms/Button";
import type { Annonce } from "../../types/annonce";
import { Link } from "react-router-dom";

interface AnnonceCardProps {
    annonce: Annonce;
}

export default function AnnonceCard({ annonce }: AnnonceCardProps) {
    return (
        <Card
            header={<h3 className="text-lg font-semibold">{annonce.url}</h3>}
            footer={
                <Link to={`/details/${annonce.id}`}>
                    <Button variant="secondary">Voir les détails</Button>
                </Link>
            }
        >
            <p className="text-gray-700 mb-2">{annonce.commentaire}</p>

            <p className="text-sm text-gray-500">
                Score transparence :{" "}
                <span className="font-bold">{annonce.scoreTransparence}</span>
            </p>

            <p className="text-sm text-gray-400">
                Signalé le : {new Date(annonce.date).toLocaleDateString()}
            </p>
        </Card>
    );
}
