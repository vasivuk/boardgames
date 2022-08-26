import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import Footer from "./Footer";
import Header from "./Header";
import Navbar from "./Navbar";

const Layout = () => {
  const [cart, setCart] = useState([]);

  return (
    <div className="App">
      <Header cart={cart} />
      <Navbar />
      <Outlet context={[cart, setCart]} />
      <Footer />
    </div>
  );
};

export default Layout;
