import React from "react";

import { Link } from "react-router-dom";
import { FiEdit } from "react-icons/fi";

const Product = ({ product }) => {
  const categoriesElement =
    product.categories &&
    product.categories.map((category) => (
      <li key={category.id}>
        <Link to={"/categories"}>{category.name}</Link>
      </li>
    ));

  return (
    <div className="p-5">
      <div className="flex items-center">
        <h1 className="p-5 text-2xl font-extrabold tracking-widest border-b text-neutral-800">
          {product.name}
        </h1>
        <Link to={"./edit"}>
          <div className="text-xl text-neutral-700 hover:text-primary-standard hover:cursor-pointer">
            <FiEdit />
          </div>
        </Link>
      </div>
      <div className="flex flex-row gap-5 p-5 border">
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
        <div className="border w-full">
          <h3 className="text-white bg-primary-standard uppercase font-semibold py-2 px-5 text-xl">
            Categories
          </h3>
          <ul className="p-2 flex flex-col gap-4">{categoriesElement}</ul>
        </div>
      </div>
      <div className="border max-w-screen-md p-5">
        <h3 className="text-white bg-primary-standard uppercase font-semibold py-2 px-5 text-xl">
          Description
        </h3>
        <p>
          Lorem ipsum dolor sit, amet consectetur adipisicing elit. Impedit
          laudantium possimus unde corrupti omnis officia! Adipisci laboriosam
          iure beatae commodi temporibus reprehenderit voluptatem dolores atque
          ex ducimus quae placeat impedit odit, natus necessitatibus minus!
          Aspernatur officia repellat modi sapiente adipisci.
        </p>
      </div>
    </div>
  );
};

export default Product;
