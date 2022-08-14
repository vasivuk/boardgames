import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <div className="bg-primary-standard flex justify-center items-center text-color_text-light drop-shadow-xl">
      <Link
        to="/boardgames"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Boardgames
      </Link>
      <Link
        to="/categories"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Categories
      </Link>
      <Link
        to="/users"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Users
      </Link>
    </div>
  );
};

export default Navbar;
