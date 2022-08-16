import React from "react";
import { FaUser } from "react-icons/fa";
import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const Header = () => {
  const { auth } = useAuth();

  return (
    <div className="bg-primary-dark flex justify-between px-10 py-6">
      {/* Logo */}
      <div className="flex items-center w-1/4">
        <Link
          to={"/"}
          className="text-color_text-light font-bold text-2xl tracking-wider hover:cursor-pointer"
        >
          Boardgames Shop
        </Link>
      </div>
      {/* Search */}
      <div className="flex items-center text-color_text-light w-1/2">
        <input
          type="text"
          name=""
          id=""
          className="text-slate-900 px-2 py-1 rounded-l-lg focus:outline-none focus:bg-slate-100 w-full"
        />
        <button className="bg-secondary-dark py-1 px-4 text-color_text-dark  font-semibold rounded-r-lg hover:bg-slate-400">
          Search
        </button>
      </div>
      {/* Login Button */}
      <div className="w-1/4 flex justify-end items-center ">
        {auth?.accessToken ? (
          <Link
            to={"/logout"}
            className="text-color_text-light flex items-center space-x-2 hover:text-yellow-400"
          >
            <FaUser />
            <p>Log Out</p>
          </Link>
        ) : (
          <Link
            to={"/login"}
            className="text-color_text-light flex items-center space-x-2 hover:text-yellow-400"
          >
            <FaUser />
            <p>Log In</p>
          </Link>
        )}
      </div>
    </div>
  );
};

export default Header;
