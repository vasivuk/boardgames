import React from "react";
import ProductCard from "../../components/ui/ProductCard";

const ProductListSection = ({ products, loading }) => {
  return (
    <div className="col-span-4 flex flex-row flex-wrap justify-start p-10 gap-5 mx-14">
      {!loading &&
        products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
    </div>
  );
};

export default ProductListSection;
