import { Routes, Route } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/Home/HomePage";
import LoginPage from "./pages/Login/LoginPage";
import RegisterPage from "./pages/Register/RegisterPage";
import ProductForm from "./pages/Product/ProductPage";
import EditProductForm from "./pages/Product/EditProductForm";
import ProductsList from "./pages/Product/ProductsList";
import CategoriesList from "./pages/Category/CategoriesList";
import Layout from "./components/Layout";
import Page404 from "./pages/Page404";
import RequireAuth from "./components/RequireAuth";
import NewProductForm from "./pages/Product/NewProductForm";
import NewCategoryForm from "./pages/Category/NewCategoryForm";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        {/* Public routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/boardgames/create" element={<NewProductForm />} />

        <Route path="/boardgames" element={<ProductsList />} />
        <Route path="/boardgames/:id/:title" element={<ProductForm />} />
        <Route
          path="/boardgames/:id/:title/edit"
          element={<EditProductForm />}
        />
        <Route path="/categories" element={<CategoriesList />} />
        <Route path="/categories/create" element={<NewCategoryForm />} />
        <Route element={<RequireAuth />}></Route>

        {/* Protected routes */}

        {/* Catch all */}
        <Route path="*" element={<Page404 />} />
      </Route>
    </Routes>
  );
}

export default App;
