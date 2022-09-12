import React from "react";
import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const Navbar = () => {
  const { auth } = useAuth();
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
        to="/cart"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Cart
      </Link>
      {auth?.accessToken && (
        <Link
          to="/checkout"
          className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
        >
          Checkout
        </Link>
      )}
    </div>
  );
};

export default Navbar;
