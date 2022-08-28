import React, { useEffect, useState } from "react";
import axios from "../../api/axios";
import ProductCard from "../../components/ui/ProductCard";

const FeaturedSection = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await axios.get("/products");
        setProducts(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);
  return (
    <div className="w-full p-10">
      <h1 className="text-center text-3xl font-bold text-color_text-dark p-5">
        Featured Games
      </h1>
      <div className="flex flex-row justify-center space-x-4 p-5">
        {!loading &&
          products
            .filter((product) => product.id <= 4)
            .map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
      </div>
    </div>
  );
};

export default FeaturedSection;
