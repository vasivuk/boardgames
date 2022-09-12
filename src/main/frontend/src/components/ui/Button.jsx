import React from "react";

const Button = ({ text, onClick, disabled, dark, stretch }) => {
  return (
    <button
      disabled={disabled}
      onClick={onClick}
      className={
        "rounded-lg text-white py-2 px-6 disabled:opacity-50 " +
        (dark
          ? "bg-green-800 enabled:hover:bg-green-900 "
          : "bg-primary-dark enabled:hover:bg-primary-light ") +
        (stretch && "w-full")
      }
    >
      {text}
    </button>
  );
};

export default Button;
