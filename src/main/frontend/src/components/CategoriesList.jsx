import React from "react";
import useRefreshToken from "../hooks/useRefreshToken";

const CategoriesList = () => {
  const refresh = useRefreshToken();
  return <button onClick={() => refresh()}>Refresh</button>;
};

export default CategoriesList;
