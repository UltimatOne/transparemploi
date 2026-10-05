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

          {/* Page de signalement */}
          <Route path="/signaler" element={<OfferReportPage />} />

          {/* Liste des offres */}
          <Route path="/offers" element={<OfferListPage />} />

          {/* Détails d'une offre */}
          <Route path="/offers/:id" element={<OfferDetailsPage />} />
        </Routes>
      </MainLayout>
    </BrowserRouter>
  );
}

export default App;
