import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import type { JSX } from "react/jsx-runtime";

export default function PrivateRoute({ children }: { children: JSX.Element }) {
    const { isLogged } = useAuth();

    if (!isLogged) {
        return <Navigate to="/" replace />;
    }

    return children;
}
