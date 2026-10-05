export default function Footer() {
    return (
        <footer
            className="
        bg-gray-100
        text-gray-600
        py-4 sm:py-6
        mt-10
        border-t
      "
            role="contentinfo"
        >
            <div
                className="
          max-w-5xl
          mx-auto
          text-center
          text-sm sm:text-base
          px-4
        "
            >
                <p className="leading-relaxed">
                    TransparEmploi — Projet de transparence des annonces d'emploi
                </p>

                <p className="mt-1">
                    © {new Date().getFullYear()} — Tous droits réservés
                </p>
            </div>
        </footer>
    );
}
