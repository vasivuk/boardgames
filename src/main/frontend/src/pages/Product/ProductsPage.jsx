import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import ProductService from "../../services/ProductService";
import FilterSection from "./FilterSection";
import ProductListSection from "./ProductListSection";

const ProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await ProductService.getAllProducts();
        setProducts(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-center text-xl py-5">Boardgames</h1>
      <Link to={"./create"}>
        <button className="px-6 py-2 bg-primary-standard text-secondary-standard rounded-md">
          Add New Product
        </button>
      </Link>

      <div className="flex gap-5">
        <FilterSection />
        <ProductListSection products={products} loading={loading} />
      </div>
    </div>
  );
};

export default ProductsPage;
