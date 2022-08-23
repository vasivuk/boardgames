import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "../../api/axios";

const CategoriesList = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    axios.get("/categories").then((response) => {
      setCategories(response?.data);
    });
  }, []);

  return (
    <div className="p-10">
      <Link to={"./create"}>
        <button className="px-6 py-2 bg-primary-standard text-color_text-light rounded-md">
          Add New Category
        </button>
      </Link>
      <br />
      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="py-3 px-6">
              Category name
            </th>
            <th scope="col" className="py-3 px-6">
              Description
            </th>
          </tr>
        </thead>
        <tbody>
          {categories.map((category) => (
            <tr
              key={category.id}
              className="bg-white border-b dark:bg-gray-900 dark:border-gray-700"
            >
              <th
                scope="row"
                className="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {category.name}
              </th>
              <td className="py-4 px-6">{category.description}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CategoriesList;
