import { useState } from "react";
import HomeTemplate from "../templates/HomeTemplate";

export default function HomePage() {
  const [searchValue, setSearchValue] = useState("");

  const handleSearch = () => {
    console.log("Recherche :", searchValue);
    // Tu pourras connecter ton backend ici
  };

  return (
    <>
      <h1 className="text-4xl text-red-500">Tailwind 4 fonctionne !</h1>
      <HomeTemplate
        searchValue={searchValue}
        onSearchChange={(e) => setSearchValue(e.target.value)}
        onSearch={handleSearch}
      />
    </>
  );
}
