import React from "react";
import { createSearchParams, useNavigate } from "react-router-dom";
import { FaUser } from "react-icons/fa";
import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { useState } from "react";
import useLogout from "../hooks/useLogout";

const Header = () => {
  const { auth } = useAuth();
  const [name, setName] = useState("");
  const logout = useLogout();
  const navigate = useNavigate();

  function handleSubmit(e) {
    e.preventDefault();
    console.log(name);
    navigate({
      pathname: "/boardgames",
      search: createSearchParams({
        name: name,
      }).toString(),
    });
  }

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
      <form
        className="flex items-center text-color_text-light w-1/2"
        onSubmit={handleSubmit}
      >
        <input
          type="text"
          name="name"
          id="name"
          className="text-slate-900 px-2 py-1 rounded-l-lg focus:outline-none focus:bg-slate-100 w-full"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button className="bg-secondary-standard py-1 px-4 text-color_text-dark  font-semibold rounded-r-lg hover:bg-secondary-light">
          Search
        </button>
      </form>
      {/* Login Button */}
      <div className="w-1/4 flex justify-end items-center ">
        {auth?.accessToken ? (
          <button
            onClick={logout}
            className="text-color_text-light flex items-center space-x-2 hover:text-yellow-400"
          >
            <FaUser />
            <p>Log Out</p>
          </button>
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
