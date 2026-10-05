import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <nav
            className="
        bg-blue-600
        text-white
        px-6
        py-4
        shadow-md
      "
            role="navigation"
            aria-label="Navigation principale"
        >
            <div
                className="
          max-w-5xl
          mx-auto
          flex
          items-center
          justify-between
        "
            >
                {/* Logo */}
                <Link
                    to="/"
                    className="
            text-xl
            sm:text-2xl
            font-bold
            tracking-wide
            focus:outline-none
            focus:ring-2
            focus:ring-white
            rounded
          "
                >
                    TransparEmploi
                </Link>

                {/* Menu */}
                <div
                    className="
            flex
            gap-4 sm:gap-6
            text-sm sm:text-base
          "
                >
                    <Link
                        to="/"
                        className="
              hover:underline
              focus:outline-none
              focus:ring-2
              focus:ring-white
              rounded
            "
                    >
                        Accueil
                    </Link>

                    <Link
                        to="/listedesannonces"
                        className="
              hover:underline
              focus:outline-none
              focus:ring-2
              focus:ring-white
              rounded
            "
                    >
                        Annonces
                    </Link>

                    <Link
                        to="/signaleruneannonce"
                        className="
              hover:underline
              focus:outline-none
              focus:ring-2
              focus:ring-white
              rounded
            "
                    >
                        Signaler
                    </Link>
                </div>
            </div>
        </nav>
    );
}
