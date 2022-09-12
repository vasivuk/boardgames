import React from "react";
import { Link } from "react-router-dom";
import Button from "./Button";

const ProductCard = ({ product }) => {
  return (
    <Link to={`/boardgames/${product.id}/${product.slug}`}>
      <div className="flex rounded-lg flex-col relative gap-2 rounded justify-between items-center p-4 h-92 w-60 text-neutral-800 border-neutral-400 border shadow-lg cursor-pointer">
        {!product.stockQuantity && (
          <div className="ribbon-wrapper">
            <div className="ribbon">Out of stock</div>
          </div>
        )}
        <img
          src={product.imageUrl}
          alt="Product"
          className={"h-52 " + (!product.stockQuantity && "opacity-50")}
        />
        <div className="flex flex-col items-center max-h-full">
          <h1 className="text-xl font-bold text-neutral-700">{product.name}</h1>
          <p className="text-lg font-semibold text-green-700">
            {product.price}$
          </p>
        </div>
        <Button text="View Details" />
      </div>
    </Link>
  );
};

export default ProductCard;
