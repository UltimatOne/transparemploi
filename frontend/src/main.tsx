import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./index.css";

const rootElement = document.getElementById("root");

if (!rootElement) {
  throw new Error("Impossible de trouver l'élément #root dans le DOM.");
}

createRoot(rootElement).render(
  <StrictMode>
    <App />
  </StrictMode>
);
