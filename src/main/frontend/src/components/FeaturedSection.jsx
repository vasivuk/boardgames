import React from "react";
import BoardgameCard from "./BoardgameCard";

const FeaturedSection = () => {
  return (
    <div className="w-full bg-secondary-standard">
      <h1 className="text-center text-3xl font-bold text-color_text-dark p-5">
        Featured Games
      </h1>
      <div className="flex flex-row justify-center space-x-4 p-5">
        <BoardgameCard />
        <BoardgameCard />
        <BoardgameCard />
        <BoardgameCard />
        <BoardgameCard />
      </div>
    </div>
  );
};

export default FeaturedSection;
