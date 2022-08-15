import React from "react";

const ErrorMessage = ({ message }) => {
  return (
    <div className="bg-red-200 text-red-800 p-2 font-semibold">{message}</div>
  );
};

export default ErrorMessage;
