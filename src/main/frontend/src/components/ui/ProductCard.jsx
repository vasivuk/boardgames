import React from "react";
import { Link } from "react-router-dom";

const ProductCard = ({ product }) => {
  return (
    <Link to={`/boardgames/${product.id}/${product.slug}`}>
      <div className="flex flex-col gap-2 rounded justify-between items-center p-2 space-y-5 h-92 w-60 text-slate-800 border-slate-400 border shadow-lg cursor-pointer">
        <div className="h-52">
          <img src={product.imageUrl} alt="Product" className="max-h-full" />
        </div>
        <div className="flex flex-col items-center max-h-full">
          <h1 className="text-xl font-bold text-neutral-700">{product.name}</h1>
          <p className="text-lg font-semibold text-green-700">
            {product.price}$
          </p>
        </div>
        <button className="px-6 py-2 text-white bg-primary-light text-lg rounded-xl hover:bg-primary-dark">
          View Details
        </button>
      </div>
    </Link>
  );
};

export default ProductCard;
