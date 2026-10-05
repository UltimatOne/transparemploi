import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import Button from "../atoms/Button";
import AuthModal from "../organisms/AuthModal";
import { AuthAPI } from "../../services/AuthAPI";
import { useAuth } from "../../context/AuthContext";

export default function Navbar() {
    const navigate = useNavigate();

    // AuthContext
    const { isLogged, login, logout } = useAuth();

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    const handleLogout = () => {
        logout();
        navigate("/");
    };

    const handleLogin = async () => {
        setIsLoading(true);

        try {
            const data = await AuthAPI.login(email, password);

            login(data.token, data.refreshToken);

            // Nettoyage automatique des champs
            setEmail("");
            setPassword("");

            setErrorMessage("");
            setSuccessMessage("Connexion réussie !");
            setIsModalOpen(false);
            navigate("/");
        } catch (err: any) {
            setErrorMessage("Email ou mot de passe incorrect.");
        } finally {
            setIsLoading(false); // 🔥 désactivation du loading
        }
    };

    return (
        <>
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
                            text-xl sm:text-2xl
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
                            items-center
                        "
                    >
                        {isLogged && (
                            <>
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
                            </>
                        )}

                        {/* Déconnexion si connecté */}
                        {isLogged && (
                            <Button
                                variant="outline"
                                className="
                                    text-white
                                    border-white
                                    hover:bg-white
                                    hover:text-blue-600
                                "
                                onClick={handleLogout}
                            >
                                Déconnexion
                            </Button>
                        )}

                        {/* Connexion si NON connecté */}
                        {!isLogged && (
                            <Button
                                variant="outline"
                                className="
                                    text-white
                                    border-white
                                    hover:bg-white
                                    hover:text-blue-600
                                "
                                onClick={() => {
                                    // 🔥 Nettoyage des messages + champs à l’ouverture
                                    setErrorMessage("");
                                    setSuccessMessage("");
                                    setEmail("");
                                    setPassword("");

                                    setIsModalOpen(true);
                                }}
                            >
                                Connexion
                            </Button>
                        )}
                    </div>
                </div>
            </nav>

            {/* Modale de connexion */}
            <AuthModal
                isOpen={isModalOpen}
                isLoading={isLoading}
                onClose={() => setIsModalOpen(false)}
                errorMessage={errorMessage}
                successMessage={successMessage}
                fields={[
                    {
                        id: "email",
                        label: "Email",
                        value: email,
                        required: true,
                        onChange: (e: React.ChangeEvent<HTMLInputElement>) =>
                            setEmail(e.target.value),
                    },
                    {
                        id: "password",
                        label: "Mot de passe",
                        type: "password",
                        value: password,
                        required: true,
                        onChange: (e: React.ChangeEvent<HTMLInputElement>) =>
                            setPassword(e.target.value),
                    },
                ]}
                onSubmit={handleLogin}
            />
        </>
    );
}
