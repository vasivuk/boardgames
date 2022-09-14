import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "../../api/axios";
import CategoryForm from "../../components/form/CategoryForm";
import ErrorMessage from "../../components/ui/ErrorMessage";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const EditCategoryPage = () => {
  const { id } = useParams();

  const axiosPrivate = useAxiosPrivate();
  const [category, setCategory] = useState({
    name: "",
    description: "",
  });

  const navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    setErrorMessage("");
  }, [category.name, category.description]);

  //Get the category when form loads
  useEffect(() => {
    axios.get(`categories/${id}`).then((response) => {
      setCategory(response.data);
    });
  }, []);

  const handleChange = (e) => {
    const value = e.target.value;
    setCategory({ ...category, [e.target.name]: value });
  };

  function handleSubmit(e) {
    e.preventDefault();
    if (category.name === "" || category.description === "") {
      setErrorMessage("Invalid product data, a field is empty");
      return;
    }

    axiosPrivate
      .put(`/categories/${category.id}`, category)
      .then((response) => {
        console.log(response?.data);
        setCategory({ name: "", description: "" });
        navigate("/categories");
      })
      .catch((error) => {
        console.log(error);
        if (!error?.response.status) {
          setErrorMessage("No Server Response");
        } else if (error.response?.status === 409) {
          setErrorMessage("Category with supplied name already exists");
        } else if (error.response?.status === 403) {
          setErrorMessage("Unauthorized Action");
        } else {
          setErrorMessage("Can't create category");
        }
      });
  }
  return (
    <div className="w-full h-screen flex justify-center items-start mt-10">
      <div className="max-w-2xl shadow border-b mx-auto bg-primary-standard text-color_text-light rounded-md p-8">
        {errorMessage && <ErrorMessage message={errorMessage} />}
        <h1 className="font-thin text-2xl tracking-wider py-3">
          Edit Category
        </h1>
        <CategoryForm
          category={category}
          handleChange={handleChange}
          handleSubmit={handleSubmit}
          btnText="Update"
        />
      </div>
    </div>
  );
};

export default EditCategoryPage;
