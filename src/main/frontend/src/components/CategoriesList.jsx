import React from "react";
import { Link } from "react-router-dom";
import useRefreshToken from "../hooks/useRefreshToken";

const CategoriesList = () => {
  const refresh = useRefreshToken();

  return (
    <div className="p-10">
      <Link to={"./create"}>
        <button className="px-6 py-2 bg-primary-standard text-secondary-standard rounded-md">
          Add New Category
        </button>
      </Link>
      <br />
      <button onClick={() => refresh()}>Refresh</button>;
    </div>
  );
};

export default CategoriesList;
