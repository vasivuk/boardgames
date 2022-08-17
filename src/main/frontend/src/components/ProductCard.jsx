import React from "react";
import { Link } from "react-router-dom";

const ProductCard = ({ product }) => {

  return (
    <Link to={`/boardgames/${product.id}/${product.slug}`}>
      <div className="flex flex-col justify-between items-center bg-slate-50 p-5 space-y-5 h-80 w-60 text-slate-800 border-slate-400 border hover:shadow-xl cursor-pointer">
        <img src={product.imageUrl} alt="Product" className="max-h-52" />
        <div className="flex flex-col items-center max-h-full">
          <h1 className="text-xl">{product.name}</h1>
          <p className="">{product.price}$</p>
        </div>
      </div>
    </Link>
  );
};

export default ProductCard;
