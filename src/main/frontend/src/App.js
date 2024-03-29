import { Routes, Route } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/Home/HomePage";
import LoginPage from "./pages/Login/LoginPage";
import RegisterPage from "./pages/Register/RegisterPage";
import ProductForm from "./pages/Product/ProductPage";
import EditProductForm from "./pages/Product/EditProductForm";
import ProductsPage from "./pages/Product/ProductsPage";
import CategoriesList from "./pages/Category/CategoriesList";
import Layout from "./components/Layout";
import Page404 from "./pages/Page404";
import RequireAuth from "./components/RequireAuth";
import NewProductForm from "./pages/Product/NewProductForm";
import NewCategoryForm from "./pages/Category/NewCategoryForm";
import PersistLogin from "./components/PersistLogin";
import Cart from "./pages/Cart/Cart";
import ProfilePage from "./pages/Profile/ProfilePage";
import CheckoutPage from "./pages/Checkout/CheckoutPage";
import CategoryPage from "./pages/Category/CategoryPage";
import EditCategoryPage from "./pages/Category/EditCategoryPage";

function App() {
  return (
    <Routes>
      <Route element={<PersistLogin />}>
        <Route path="/" element={<Layout />}>
          {/* Public routes */}
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />

          <Route path="/boardgames" element={<ProductsPage />} />
          <Route path="/boardgames/:id/:title" element={<ProductForm />} />
          <Route path="/categories" element={<CategoriesList />} />
          <Route path="/categories/:id/:title" element={<CategoryPage />} />

          <Route path="/cart" element={<Cart />} />

          {/* Protected routes */}
          <Route element={<RequireAuth allowedRole={"ADMIN"} />}>
            <Route path="/boardgames/create" element={<NewProductForm />} />
            <Route path="/categories/create" element={<NewCategoryForm />} />
            <Route
              path="/boardgames/:id/:title/edit"
              element={<EditProductForm />}
            />
            <Route
              path="/categories/:id/:title/edit"
              element={<EditCategoryPage />}
            />
          </Route>
          <Route element={<RequireAuth />}>
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/checkout" element={<CheckoutPage />} />
          </Route>

          {/* Catch all */}
          <Route path="*" element={<Page404 />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default App;
