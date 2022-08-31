import React from "react";
import { createSearchParams, useNavigate } from "react-router-dom";
import { FaUser, FaShoppingCart } from "react-icons/fa";
import { FiLogIn } from "react-icons/fi";
import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { useState } from "react";
import { ReactComponent as ReactLogo } from "../images/logo.svg";
import pingu from "../images/pingu.png";
import pinguILogo from "../images/pinguILogo.png";

const Header = ({ cart }) => {
  const { auth } = useAuth();
  const [name, setName] = useState("");
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
      <div className="w-1/4 flex justify-start">
        <Link to={"/"} className="flex items-end gap-1 rounded-xl">
          <ReactLogo className="h-11" />
          <div className="h-10">
            <img src={pingu} alt="Pingu Games" className="max-h-full" />
          </div>
          {/* <img src={pinguILogo} alt="" className="h-12" /> */}
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
      <div className="w-1/4 flex justify-end items-center gap-5 ">
        <Link to={"/cart"}>
          <div className="flex items-center gap-1 hover:bg-primary-light p-2 rounded-xl hover:cursor-pointer">
            <FaShoppingCart className="text-white" />
            <span className="bg-white text-color_text-dark rounded-md px-1">
              {cart.length}
            </span>
          </div>
        </Link>
        {auth?.accessToken ? (
          <Link
            to={"/profile"}
            className="text-color_text-light flex items-center space-x-2 hover:text-yellow-400"
          >
            <FaUser />
            <p>Profile</p>
          </Link>
        ) : (
          <Link
            to={"/login"}
            className="text-color_text-light flex items-center space-x-2 hover:text-yellow-400"
          >
            <FiLogIn />
            <p>Log In</p>
          </Link>
        )}
      </div>
    </div>
  );
};

export default Header;
