import React from "react";

const Button = ({ text, operation }) => {
  return (
    <button
      onClick={operation}
      className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 px-6 hover:bg-secondary-dark"
    >
      {text}
    </button>
  );
};

export default Button;
