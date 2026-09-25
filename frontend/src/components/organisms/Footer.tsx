export default function Footer() {
    return (
        <footer className="bg-gray-100 text-gray-600 py-4 mt-10 border-t">
            <div className="max-w-5xl mx-auto text-center text-sm">
                TransparEmploi — Projet de transparence des annonces d'emploi
                <br />
                © {new Date().getFullYear()} — Tous droits réservés
            </div>
        </footer>
    );
}
