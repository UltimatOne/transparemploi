import React, { createContext, useContext, useState, useEffect } from "react";

interface AuthContextValue {
    isLogged: boolean;
    login: (token: string, refreshToken: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const [isLogged, setIsLogged] = useState<boolean>(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsLogged(!!token);
    }, []);

    const login = (token: string, refreshToken: string) => {
        localStorage.setItem("token", token);
        localStorage.setItem("refresh", refreshToken);
        setIsLogged(true);
    };

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("refresh");
        setIsLogged(false);
    };

    return (
        <AuthContext.Provider value={{ isLogged, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const ctx = useContext(AuthContext);
    if (!ctx) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return ctx;
}
