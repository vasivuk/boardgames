import React, { useEffect, useState } from "react";
import ProductCard from "./ProductCard";
import data from "../test-data/BoardgameData";
import ProductService from "../services/ProductService";

const FeaturedSection = () => {
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
    <div className="w-full">
      <h1 className="text-center text-3xl font-bold text-color_text-dark p-5">
        Featured Games
      </h1>
      <div className="flex flex-row justify-center space-x-4 p-5">
        {!loading &&
          products.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
      </div>
    </div>
  );
};

export default FeaturedSection;
