import Card from "../atoms/Card";
import Button from "../atoms/Button";
import type { JobOfferResponse } from "../../types/JobOffer";

interface OfferCardProps {
    offer: JobOfferResponse;
}

export default function OfferCard({ offer }: OfferCardProps) {
    return (
        <Card
            header={
                <h3 className="text-lg sm:text-xl font-semibold text-gray-900">
                    {offer.title}
                </h3>
            }
            footer={
                <Button
                    to={`/annonce/${offer.id}`}
                    variant="secondary"
                    className="w-full sm:w-auto"
                >
                    Voir les détails
                </Button>
            }
        >
            <p className="text-gray-700 mb-2 text-sm sm:text-base">
                <span className="font-semibold">Entreprise :</span> {offer.company}
            </p>

            <p className="text-gray-700 mb-2 text-sm sm:text-base">
                <span className="font-semibold">Lieu :</span> {offer.location}
            </p>

            <p className="text-gray-700 mb-2 text-sm sm:text-base">
                {offer.description}
            </p>

            <p className="text-sm text-gray-500">
                Transparente :{" "}
                <span className="font-bold">
                    {offer.transparent ? "Oui" : "Non"}
                </span>
            </p>
        </Card>
    );
}
