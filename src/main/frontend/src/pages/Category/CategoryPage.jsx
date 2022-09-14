import React, { useState } from "react";
import { useEffect } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import axios from "../../api/axios";
import DeleteModal from "../../components/ui/DeleteModal";
import useAuth from "../../hooks/useAuth";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { FiEdit } from "react-icons/fi";

const CategoryPage = () => {
  const [category, setCategory] = useState({ title: "", description: "" });
  const { id } = useParams();

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  //Fetching category
  useEffect(() => {
    axios
      .get(`/categories/${id}`)
      .then((response) => setCategory(response?.data))
      .catch((error) => console.log(error));
  }, []);

  const { auth } = useAuth();

  //Delete category
  function handleDelete() {
    axiosPrivate
      .delete(`/categories/${category.id}`)
      .then(() => {
        alert("Category successfully deleted");
        navigate("/categories");
      })
      .catch((error) => console.log(error));
  }

  return (
    <div className="min-h-screen flex flex-col items-center bg-gray-200 shadow-xl">
      <div className="w-3/4 flex-1 bg-white p-10 text-neutral-700">
        <div className="flex items-center border-b">
          <h1 className="text-xl font-bold uppercase p-2">
            Category: {category.name}
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
                message="Are you sure you want to delete this category?"
              />
            </div>
          )}
        </div>
        <p className="mt-5">Description: {category.description}</p>
      </div>
    </div>
  );
};

export default CategoryPage;
