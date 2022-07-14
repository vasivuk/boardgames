import React from "react";

const BoardgameCard = () => {
  return (
    <div className="flex flex-col justify-between items-center bg-slate-50 p-5 space-y-5 h-80 w-60 text-slate-800 mb-32 border-slate-400 border hover:shadow-xl">
      <div className="imagePlaceholder w-full h-full bg-yellow-200"></div>
      <div className="flex flex-col items-center">
        <h1 className="text-xl">Title</h1>
        <p className="">50$</p>
      </div>
    </div>
  );
};

export default BoardgameCard;
