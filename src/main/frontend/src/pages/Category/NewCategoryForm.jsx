import React, { useEffect } from "react";
import { useState } from "react";
import CategoryService from "../../services/CategoryService";
import ErrorMessage from "../../components/ui/ErrorMessage";

const NewCategoryForm = () => {
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

    CategoryService.createCategory(category)
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
          <form className="flex flex-col gap-3">
            <div className="items-center justify-center h-14 w-full my-4">
              <label
                htmlFor="name"
                className="block text-color_text-light text-sm font-normal"
              >
                Category name:
              </label>
              <input
                type="text"
                id="name"
                name="name"
                placeholder="New Category"
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
                value={category.name}
                onChange={handleChange}
              />
            </div>
            <div className="items-center justify-center h-64 w-full my-4">
              <label
                htmlFor="description"
                className="block text-color_text-light text-sm font-normal"
              >
                Description
              </label>
              <textarea
                id="description"
                name="description"
                className="h-full w-96 border my-2 px-2 py-2 text-color_text-dark"
                placeholder="Cool description..."
                value={category.description}
                onChange={handleChange}
              />
            </div>
            <button
              disabled={category.name === "" || category.description === ""}
              onClick={handleSubmit}
              className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 my-5 w-full enabled:hover:bg-secondary-dark disabled:opacity-50"
            >
              Create
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default NewCategoryForm;
