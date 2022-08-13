import React from "react";
import BoardgameCard from "./BoardgameCard";
import data from "../test-data/BoardgameData";

const FeaturedSection = () => {
  console.log(data);
  const featuredBoardgames = data.map((boardgame) => (
    <BoardgameCard product={boardgame} key={boardgame.id} />
  ));
  return (
    <div className="w-full">
      <h1 className="text-center text-3xl font-bold text-color_text-dark p-5">
        Featured Games
      </h1>
      <div className="flex flex-row justify-center space-x-4 p-5">
        {featuredBoardgames}
      </div>
    </div>
  );
};

export default FeaturedSection;
