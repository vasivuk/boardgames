import React from "react";
import FeaturedSection from "./FeaturedSection";
import HeroSection from "./HeroSection";

const HomePage = () => {
  return (
    <div className="min-h-screen flex flex-col">
      <HeroSection />
      <FeaturedSection />
    </div>
  );
};

export default HomePage;
