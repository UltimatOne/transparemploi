import { useState } from "react";
import HomeTemplate from "../templates/HomeTemplate";

export default function HomePage() {
  const [searchValue, setSearchValue] = useState("");

  const handleSearch = () => {
    console.log("Recherche :", searchValue);
    // Tu pourras connecter ton backend ici
  };

  return (
    <HomeTemplate
      searchValue={searchValue}
      onSearchChange={(e) => setSearchValue(e.target.value)}
      onSearch={handleSearch}
    />
  );
}
