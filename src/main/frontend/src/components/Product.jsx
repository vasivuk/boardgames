import React from "react";
import { FiEdit } from "react-icons/fi";

const Product = ({ product }) => {
  return (
    <div className="p-5">
      <div></div>
      <h1 className="p-5 text-2xl font-extrabold border-b">{product.name}</h1>
      <FiEdit />
      <div className="flex flex-row gap-5 p-5">
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
            <tr className="border">
              <td>Price</td>
              <td>{product.price}$</td>
            </tr>
            <tr className="border">
              <td>Game time</td>
              <td>{product.gameTime}</td>
            </tr>
            <tr className="border">
              <td>Number of players</td>
              <td>{product.numberOfPlayers}</td>
            </tr>
            <tr className="border">
              <td>Rating</td>
              <td>{product.rating}</td>
            </tr>
            <tr className="border">
              <td>Complexity</td>
              <td>{product.complexity}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Product;
