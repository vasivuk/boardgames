import { Routes, Route } from "react-router-dom";
import "./App.css";
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import ProductForm from "./components/ProductPage";
import ProductsList from "./components/ProductsList";
import CategoriesList from "./components/CategoriesList";
import Layout from "./components/Layout";
import Page404 from "./components/Page404";
import RequireAuth from "./components/RequireAuth";

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
        <Route element={<RequireAuth />}>
          <Route path="/categories" element={<CategoriesList />} />
        </Route>

        {/* Protected routes */}

        {/* Catch all */}
        <Route path="*" element={<Page404 />} />
      </Route>
    </Routes>
  );
}

export default App;
