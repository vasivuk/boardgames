import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Footer from "./components/Footer";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import Navbar from "./components/Navbar";
import ProductForm from "./components/ProductPage";
import ProductsList from "./components/ProductsList";
import CategoriesList from "./components/CategoriesList";
import Layout from "./components/Layout";
import Page404 from "./components/Page404";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        {/* Public routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route path="/boardgames" element={<ProductsList />} />
        <Route path="/boardgames/:id/:title" element={<ProductForm />} />
        <Route path="/categories" element={<CategoriesList />} />

        {/* Protected routes */}

        {/* Catch all */}
        <Route path="*" element={<Page404 />} />
      </Route>
    </Routes>
  );
}

export default App;
