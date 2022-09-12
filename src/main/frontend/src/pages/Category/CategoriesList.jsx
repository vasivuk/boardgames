import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "../../api/axios";
import Button from "../../components/ui/Button";
import useAuth from "../../hooks/useAuth";

const CategoriesList = () => {
  const [categories, setCategories] = useState([]);

  const { auth } = useAuth();

  const navigate = useNavigate();

  useEffect(() => {
    axios.get("/categories").then((response) => {
      setCategories(response?.data);
    });
  }, []);

  function handleCategoryClick(category) {
    navigate(`./${category.id}/${category.name}`);
  }

  return (
    <div className="bg-neutral-100 flex flex-col items-center">
      <div className="p-10 w-full min-h-screen bg-white flex flex-col items-center shadow-xl lg:w-3/4 ">
        <h1 className="text-3xl text-neutral-600 font-bold uppercase p-4">
          Categories
        </h1>
        {auth?.role === "ADMIN" && (
          <Link to={"./create"}>
            <Button text="Add a New Category" />
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
                className="bg-white border-b hover:bg-neutral-200 hover: cursor-pointer"
                onClick={() => handleCategoryClick(category)}
              >
                <th
                  scope="row"
                  className="py-4 px-6 font-medium text-gray-900 whitespace-nowrap"
                >
                  {category.name}
                </th>
                <td className="py-4 px-6">{category.description}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CategoriesList;
