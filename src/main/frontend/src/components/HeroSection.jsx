import React from "react";
import { GiAbbotMeeple } from "react-icons/gi";

const HeroSection = () => {
  return (
    <div className="h-96 flex bg-secondary-dark items-center justify-between z-0">
      {/* Left */}
      <div className="flex flex-col justify-center p-20 text-color_text-dark space-y-6">
        <h1 className="text-5xl font-extrabold">New arrivals!</h1>
        <p className="text-xl font-semibold max-w-md text-color_text-dark  ">
          Lorem, ipsum dolor sit amet consectetur adipisicing elit. Optio dolore
          eaque mollitia est explicabo recusandae cumque.
        </p>
      </div>
      {/* Right */}
      <div className="text-8xl text-color_text-dark  px-60">
        <GiAbbotMeeple />
      </div>
    </div>
  );
};

export default HeroSection;
