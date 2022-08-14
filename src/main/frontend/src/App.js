import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import Navbar from "./components/Navbar";
import ProductForm from "./components/ProductPage";
import ProductsList from "./components/ProductsList";
import CategoriesList from "./components/CategoriesList";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/boardgames/:id/:title" element={<ProductForm />} />
        <Route path="/boardgames" element={<ProductsList />} />
        <Route path="/categories" element={<CategoriesList />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
