import React from "react";

const HeroSection = () => {
  return (
    <div className="flex items-center bg-[url('../public/images/hero-section.jpg')] bg-cover bg-no-repeat bg-center justify-between">
      {/* Left */}
      <div className="flex flex-col justify-center p-40 text-neutral-700 shadow space-y-6">
        <h1 className="text-5xl font-extrabold">New Arrivals!</h1>
        <p className="text-xl font-semibold max-w-md text-neutral-600">
          Check out the new arrivals in the featured section!
        </p>
      </div>
    </div>
  );
};

export default HeroSection;
