import React from "react";

const Navbar = () => {
  return (
    <div className="bg-primary-standard flex justify-center items-center text-color_text-light drop-shadow-xl">
      <a
        href="/#"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Boardgames
      </a>
      <a
        href="/#"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Categories
      </a>
      <a
        href="/#"
        className="font-semibold tracking-wider py-3 px-6 hover:bg-primary-light"
      >
        Users
      </a>
    </div>
  );
};

export default Navbar;
