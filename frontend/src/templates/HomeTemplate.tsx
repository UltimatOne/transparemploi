export default function HomeTemplate() {
    return (
        <section
            aria-labelledby="home-title"
            className="max-w-4xl mx-auto py-16 px-4 sm:px-6 lg:px-8 text-center"
        >
            <h1
                id="home-title"
                className="text-4xl font-bold mb-6 text-gray-900"
            >
                Bienvenue sur TransparEmploi
            </h1>

            <p className="text-lg text-gray-700 leading-relaxed max-w-2xl mx-auto">
                Découvrez des offres d’emploi vérifiées, transparentes et fiables.
                Notre mission : vous aider à trouver un emploi en toute confiance.
            </p>

            <p className="mt-6 text-gray-600">
                Utilisez le menu pour parcourir les annonces ou signaler une offre.
            </p>
        </section>
    );
}
