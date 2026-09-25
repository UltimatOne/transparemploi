import Card from "../atoms/Card";
import Button from "../atoms/Button";
import type { JobOfferResponse } from "../../types/JobOffer";
import { Link } from "react-router-dom";

interface OfferCardProps {
    offer: JobOfferResponse;
}

export default function OfferCard({ offer }: OfferCardProps) {
    return (
        <Card
            header={<h3 className="text-lg font-semibold">{offer.title}</h3>}
            footer={
                <Link to={`/offers/${offer.id}`}>
                    <Button variant="secondary">Voir les détails</Button>
                </Link>
            }
        >
            <p className="text-gray-700 mb-2">
                <span className="font-semibold">Entreprise :</span> {offer.company}
            </p>

            <p className="text-gray-700 mb-2">
                <span className="font-semibold">Lieu :</span> {offer.location}
            </p>

            <p className="text-gray-700 mb-2">{offer.description}</p>

            <p className="text-sm text-gray-500">
                Transparente :{" "}
                <span className="font-bold">
                    {offer.transparent ? "Oui" : "Non"}
                </span>
            </p>
        </Card>
    );
}
