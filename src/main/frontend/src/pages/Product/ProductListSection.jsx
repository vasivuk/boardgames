import React from "react";
import ProductCard from "../../components/ui/ProductCard";

const ProductListSection = ({ products, loading }) => {
  return (
    <div className="flex flex-row flex-wrap justify-start gap-5">
      {!loading &&
        products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
    </div>
  );
};

export default ProductListSection;
