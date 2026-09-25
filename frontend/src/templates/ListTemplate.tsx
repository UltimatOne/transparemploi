import AnnonceList from "../components/organisms/AnnonceList";

export default function ListTemplate() {
    return (
        <div className="max-w-5xl mx-auto py-10 px-4">
            <h1 className="text-2xl font-bold mb-6">Toutes les annonces signalées</h1>

            <AnnonceList />
        </div>
    );
}
