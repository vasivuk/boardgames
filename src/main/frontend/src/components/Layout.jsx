import React, { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import Footer from "./Footer";
import Header from "./Header";
import Navbar from "./Navbar";

const Layout = () => {
  const [cart, setCart] = useState(
    JSON.parse(window.localStorage.getItem("cart")) || []
  );

  useEffect(() => {
    setCart(JSON.parse(window.localStorage.getItem("cart")));
  }, []);

  useEffect(() => {
    window.localStorage.setItem("cart", JSON.stringify(cart));
  }, [cart]);

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
