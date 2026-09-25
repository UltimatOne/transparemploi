import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <nav className="bg-blue-600 text-white px-6 py-4 shadow-md">
            <div className="max-w-5xl mx-auto flex items-center justify-between">
                <Link to="/" className="text-xl font-bold">
                    TransparEmploi
                </Link>

                <div className="flex gap-6">
                    <Link to="/" className="hover:underline">
                        Accueil
                    </Link>
                    <Link to="/list" className="hover:underline">
                        Annonces
                    </Link>
                    <Link to="/report" className="hover:underline">
                        Signaler
                    </Link>
                </div>
            </div>
        </nav>
    );
}
