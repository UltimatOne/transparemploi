import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import HomePage from "./pages/HomePage";
import ReportPage from "./pages/ReportPage";
import ListPage from "./pages/ListPage";
import DetailsPage from "./pages/DetailsPage";

function App() {
  return (
    <BrowserRouter>
      <MainLayout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/signaler" element={<ReportPage />} />
          <Route path="/liste" element={<ListPage />} />
          <Route path="/details/:id" element={<DetailsPage />} />
        </Routes>
      </MainLayout>
    </BrowserRouter>
  );
}

export default App;
