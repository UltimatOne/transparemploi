import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";

// Pages
import HomePage from "./pages/HomePage";
import OfferReportPage from "./pages/OfferReportPage";
import OfferListPage from "./pages/OfferListPage";
import OfferDetailsPage from "./pages/OfferDetailsPage";

function App() {
  return (
    <BrowserRouter>
      <MainLayout>
        <Routes>
          {/* Page d'accueil */}
          <Route path="/" element={<HomePage />} />

          {/* Liste des annonces */}
          <Route path="/listedesannonces" element={<OfferListPage />} />

          {/* Détails d'une annonces */}
          <Route path="/annonce/:id" element={<OfferDetailsPage />} />

          {/* Page de signalement */}
          <Route path="/signaleruneannonce" element={<OfferReportPage />} />

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
  );
}

export default App;
