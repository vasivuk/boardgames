import React, { useEffect } from "react";
import { useState } from "react";
import CategoryForm from "../../components/form/CategoryForm";
import ErrorMessage from "../../components/ui/ErrorMessage";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const NewCategoryForm = () => {
  const axiosPrivate = useAxiosPrivate();
  const [category, setCategory] = useState({
    name: "",
    description: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    setErrorMessage("");
  }, [category.name, category.description]);

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
      .post("/categories/create", category)
      .then((response) => {
        console.log(response?.data);
        setSuccess(true);
        setCategory({ name: "", description: "" });
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
        <h1 className="font-thin text-2xl tracking-wider py-3">New Category</h1>
        {success ? (
          <div>
            <h1 className="font-semibold">Category successfully created!</h1>
            <button
              className="underline hover:text-yellow-400"
              onClick={() => setSuccess(false)}
            >
              Create more categories
            </button>
          </div>
        ) : (
          <CategoryForm
            category={category}
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            btnText="Create"
          />
        )}
      </div>
    </div>
  );
};

export default NewCategoryForm;
