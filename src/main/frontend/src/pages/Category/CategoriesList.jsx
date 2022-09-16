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

  const [categoryName, setCategoryName] = useState("");

  useEffect(() => {
    axios.get("/categories").then((response) => {
      setCategories(response?.data);
    });
  }, []);

  function handleCategoryClick(category) {
    navigate(`./${category.id}/${category.name}`);
  }

  useEffect(() => {
    handleCategorySearch();
  }, [categoryName]);

  function handleCategorySearch() {
    axios
      .get(`/categories/name?search=${categoryName}`)
      .then((response) => setCategories(response?.data))
      .catch(() => setCategories([]));
  }

  return (
    <div className="bg-neutral-100 flex flex-col items-center">
      <div className="p-10 w-full min-h-screen bg-white flex flex-col gap-5 items-center shadow-xl lg:w-3/4 ">
        <h1 className="text-3xl text-neutral-600 font-bold uppercase">
          Categories
        </h1>
        {auth?.role === "ADMIN" && (
          <Link to={"./create"}>
            <Button text="Add a New Category" />
          </Link>
        )}
        <div className="p-2 rounded-lg bg-primary-light">
          <input
            type="text"
            name="categoryName"
            id="name"
            placeholder="Search Categories..."
            className="text-slate-900 px-2 py-1 bg-neutral-100 rounded-lg focus:outline-none focus:bg-neutral-200 w-full"
            value={categoryName}
            onChange={(e) => setCategoryName(e.target.value)}
          />
        </div>
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
        {categories.length === 0 && (
          <p className="p-5">No categories containing: {categoryName}</p>
        )}
      </div>
    </div>
  );
};

export default CategoriesList;
