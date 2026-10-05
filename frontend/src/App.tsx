import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import MainLayout from "./layouts/MainLayout";

// Pages
import HomePage from "./pages/HomePage";
import OfferReportPage from "./pages/OfferReportPage";
import OfferListPage from "./pages/OfferListPage";
import OfferDetailsPage from "./pages/OfferDetailsPage";

import PrivateRoute from "./router/PrivateRoute";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <MainLayout>
          <Routes>
            {/* Page d'accueil */}
            <Route path="/" element={<HomePage />} />

            {/* Liste des annonces (protégée) */}
            <Route
              path="/listedesannonces"
              element={
                <PrivateRoute>
                  <OfferListPage />
                </PrivateRoute>
              }
            />

            {/* Détails d'une annonce (protégée) */}
            <Route
              path="/annonce/:id"
              element={
                <PrivateRoute>
                  <OfferDetailsPage />
                </PrivateRoute>
              }
            />

            {/* Page de signalement (protégée) */}
            <Route
              path="/signaleruneannonce"
              element={
                <PrivateRoute>
                  <OfferReportPage />
                </PrivateRoute>
              }
            />

            {/* Route fallback */}
            <Route
              path="*"
              element={
                <div className="text-center py-20 text-gray-700">
                  Page introuvable.
                </div>
              }
            />
          </Routes>
        </MainLayout>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
