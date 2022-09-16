import React from "react";
import ProductCard from "../../components/ui/ProductCard";

const ProductListSection = ({ products, loading }) => {
  return (
    <div className="flex flex-row flex-wrap justify-start gap-5">
      {!loading && products.length === 0 ? (
        <p className="font-semibold p-2 text-xl">
          No products in store with selected filters...
        </p>
      ) : (
        products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))
      )}
    </div>
  );
};

export default ProductListSection;
