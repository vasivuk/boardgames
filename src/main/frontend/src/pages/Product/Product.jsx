import React from "react";

import { Link } from "react-router-dom";
import { FiEdit } from "react-icons/fi";
import DeleteModal from "../../components/ui/DeleteModal";
import { FaStar } from "react-icons/fa";
import useAuth from "../../hooks/useAuth";

const Product = ({ product, handleDelete }) => {
  const categoriesElement =
    product.categories &&
    product.categories.map((category) => (
      <li key={category.id}>
        <Link to={"/categories"}>{category.name}</Link>
      </li>
    ));

  const { auth } = useAuth();

  return (
    <div className="p-5">
      <div className="flex items-center border-b">
        <h1 className="m-5 px-5 text-3xl font-extrabold tracking-widest text-neutral-700">
          {product.name}
        </h1>
        {auth?.role === "ADMIN" && (
          <div className="flex gap-2">
            <Link to={"./edit"}>
              <div className="text-xl opacity-50 text-neutral-700 hover:text-primary-standard hover:cursor-pointer hover:opacity-100 mx-2">
                <FiEdit />
              </div>
            </Link>
            <DeleteModal
              handleDelete={handleDelete}
              message="Are you sure you want to delete this product?"
            />
          </div>
        )}
      </div>

      <div className="flex flex-row gap-5 m-5">
        <img src={product.imageUrl} alt="" className="w-96 max-h-fit" />

        <table className="table-auto border w-full">
          <thead className="p-10 text-xl bg-primary-standard">
            <tr>
              <td
                className="text-center text-color_text-light p-2 uppercase font-semibold"
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
              <td>
                <div className="flex items-center gap-1">
                  <FaStar className="text-yellow-500 text-xl" />
                  <span>{product.rating}/5</span>
                </div>
              </td>
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
