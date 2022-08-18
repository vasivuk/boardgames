import React from "react";

import { Link } from "react-router-dom";
import { FiEdit } from "react-icons/fi";
import DeleteProductModal from "./DeleteProductModal";

const Product = ({ product, handleDelete }) => {
  const categoriesElement =
    product.categories &&
    product.categories.map((category) => (
      <li key={category.id}>
        <Link to={"/categories"}>{category.name}</Link>
      </li>
    ));

  return (
    <div className="p-5">
      <div className="flex items-center border-b">
        <h1 className="m-5 px-5 text-3xl font-extrabold tracking-widest text-neutral-700">
          {product.name}
        </h1>
        <Link to={"./edit"}>
          <div className="text-xl text-neutral-700 hover:text-primary-standard hover:cursor-pointer mx-2">
            <FiEdit />
          </div>
        </Link>
        <DeleteProductModal handleDelete={handleDelete} />
      </div>
      <div className="flex flex-row gap-5 m-5">
        <img src={product.imageUrl} alt="" className="w-96 max-h-fit" />
        <table className="table-auto border w-full">
          <thead className="p-10 text-xl bg-primary-standard">
            <tr>
              <td
                className="text-center text-secondary-standard p-2 uppercase font-semibold"
                colSpan={2}
              >
                Basic info
              </td>
            </tr>
          </thead>
          <tbody className="">
            <tr className="border-b">
              <td className="px-5">Price</td>
              <td>{product.price}$</td>
            </tr>
            <tr className="border-b">
              <td className="px-5">Game time</td>
              <td>{product.gameTime}</td>
            </tr>
            <tr className="border-b">
              <td className="px-5">Number of players</td>
              <td>{product.numberOfPlayers}</td>
            </tr>
            <tr className="border-b">
              <td className="px-5">Rating</td>
              <td>{product.rating}</td>
            </tr>
            <tr className="border-b">
              <td className="px-5">Complexity</td>
              <td>{product.complexity}</td>
            </tr>
          </tbody>
        </table>
        <div className="border w-full">
          <h3 className="text-white bg-primary-standard uppercase font-semibold py-2 px-5 text-xl">
            Categories
          </h3>
          <ul className="p-2 flex flex-col gap-4">{categoriesElement}</ul>
        </div>
      </div>
      <div className="border max-w-screen-md m-5">
        <h3 className="text-white bg-primary-standard uppercase font-semibold py-2 px-5 text-xl">
          Description
        </h3>
        <p className="p-4">{product.description}</p>
      </div>
    </div>
  );
};

export default Product;
