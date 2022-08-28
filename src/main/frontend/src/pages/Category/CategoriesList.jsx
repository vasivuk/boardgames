import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "../../api/axios";
import DeleteModal from "../../components/ui/DeleteModal";
import useAuth from "../../hooks/useAuth";

const CategoriesList = () => {
  const [categories, setCategories] = useState([]);

  const { auth } = useAuth();

  useEffect(() => {
    axios.get("/categories").then((response) => {
      setCategories(response?.data);
    });
  }, []);

  function handleDelete(category) {
    console.log(category);
  }

  return (
    <div className="bg-neutral-100 flex flex-col items-center">
      <div className="p-10 w-full min-h-screen bg-white flex flex-col items-center shadow-xl lg:w-3/4 ">
        <h1 className="text-3xl text-neutral-600 font-bold uppercase p-4">
          Categories
        </h1>
        {auth?.role === "ADMIN" && (
          <Link to={"./create"}>
            <button className="px-6 py-2 bg-primary-standard text-color_text-light rounded-md">
              Add New Category
            </button>
          </Link>
        )}
        <br />
        <table className="w-full text-sm text-left text-gray-500">
          <thead className="text-xs text-white uppercase bg-primary-light">
            <tr>
              <th scope="col" className="py-3 px-6">
                Category name
              </th>
              <th scope="col" className="py-3 px-6">
                Description
              </th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {categories.map((category) => (
              <tr
                key={category.id}
                className="bg-white border-b hover:bg-neutral-100"
              >
                <th
                  scope="row"
                  className="py-4 px-6 font-medium text-gray-900 whitespace-nowrap"
                >
                  {category.name}
                </th>
                <td className="py-4 px-6">{category.description}</td>
                <td className="py-4 px-6">
                  <DeleteModal
                    handleDelete={() => handleDelete(category)}
                    message="Are you sure you want to delete this category?"
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CategoriesList;
