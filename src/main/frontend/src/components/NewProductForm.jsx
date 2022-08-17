import React, { useEffect, useState } from "react";
import ProductService from "../services/ProductService";
import ErrorMessage from "./ErrorMessage";
import FormInput from "./FormInput";
import genericIcon from "../images/generic-boardgame-icon.png";
import CategoryService from "../services/CategoryService";
import CategoriesModal from "./CategoriesModal";
import { MdRemoveCircle } from "react-icons/md";

const NewProductForm = () => {
  const [categories, setCategories] = useState([]);

  const [selectedCategories, setSelectedCategories] = useState([]);

  const [product, setProduct] = useState({
    name: "",
    description: "",
    price: 0,
    imageUrl: "",
    complexity: 0,
    numberOfPlayers: "",
    gameTime: 0,
    rating: 0,
    categories: [],
  });

  const addCategory = function (category) {
    if (selectedCategories.includes(category)) {
      console.log("Category already added");
    } else {
      setSelectedCategories((prevSelectedCategories) => [
        ...prevSelectedCategories,
        category,
      ]);
      setProduct({ ...product, categories: selectedCategories });
    }
  };

  const removeCategory = function (category) {
    if (selectedCategories.includes(category)) {
      setSelectedCategories(
        selectedCategories.filter((c) => c.id !== category.id)
      );
      setProduct({ ...product, categories: selectedCategories });
    } else {
      console.log("Category is not in the list");
    }
  };
  console.log(product);

  const [errorMessage, setErrorMessage] = useState("");
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    CategoryService.getAllCategories().then((response) => {
      setCategories(response?.data);
    });
  }, []);

  useEffect(() => {
    setErrorMessage("");
  }, [product]);

  const handleChange = (e) => {
    const value = e.target.value;
    setProduct({ ...product, [e.target.name]: value });
  };

  function handleSubmit(e) {
    e.preventDefault();
    if (
      product.name === "" ||
      product.description === "" ||
      product.price <= 0
    ) {
      setErrorMessage("Invalid product data, a field is empty");
      return;
    }

    ProductService.createProduct(product)
      .then((response) => {
        console.log(response?.data);
        setSuccess(true);
      })
      .catch((error) => {
        console.log(error);
        if (!error?.response.status) {
          setErrorMessage("No Server Response");
        } else if (error.response?.status === 409) {
          setErrorMessage("Product with supplied name already exists");
        } else if (error.response?.status === 403) {
          setErrorMessage("Unauthorized Action");
        } else {
          setErrorMessage("Can't create product");
        }
      });
  }
  return (
    <div className="w-full flex justify-center mx-auto items-start my-10">
      <div className="shadow border-b bg-primary-standard text-color_text-light rounded-md p-8">
        {errorMessage && <ErrorMessage message={errorMessage} />}
        <h1 className="font-thin text-2xl tracking-wider mb-4 pb-2 border-b-primary-dark border-b-2">
          New Product
        </h1>
        {success ? (
          <div>
            <h1 className="font-semibold">Product successfully created!</h1>
            <button
              className="underline hover:text-yellow-400"
              onClick={() => setSuccess(false)}
            >
              Create more products
            </button>
          </div>
        ) : (
          <div>
            <div className="flex flex-row gap-5">
              <div className="w-96 justify-self-center rounded-xl">
                <img
                  src={product.imageUrl || genericIcon}
                  alt=""
                  className="max-h-96 rounded-lg border-primary-dark border"
                />
                <FormInput
                  type="text"
                  name="imageUrl"
                  label={"Image source: "}
                  onChange={handleChange}
                  placeholder="https://randomImage.jpg"
                  value={product.imageUrl}
                />
                <div>
                  <div className="flex mt-3 py-2 justify-between items-center border-b border-primary-dark">
                    <h3 className="">Categories</h3>
                    <CategoriesModal
                      categories={categories}
                      alreadyAddedCategories={selectedCategories}
                      addCategory={addCategory}
                    />
                  </div>
                  {selectedCategories.map((category) => (
                    <div
                      key={category?.id}
                      className="border-b border-primary-dark p-2 flex justify-between items-center"
                    >
                      <div>
                        <p className="text-lg">{category.name}</p>
                        <p className="text-sm">{category.description}</p>
                      </div>
                      <div
                        className="text-2xl text-white hover:text-red-500"
                        onClick={() => removeCategory(category)}
                      >
                        <MdRemoveCircle />
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              <form className="flex flex-col gap-1">
                <FormInput
                  type="text"
                  name="name"
                  label={"Name: "}
                  onChange={handleChange}
                  placeholder="New Product"
                  value={product.name}
                />
                <FormInput
                  type="number"
                  name="price"
                  label={"Price: "}
                  onChange={handleChange}
                  placeholder="50"
                  value={product.price}
                />
                <FormInput
                  type="number"
                  name="complexity"
                  label={"Complexity: "}
                  onChange={handleChange}
                  placeholder="2.4"
                  value={product.complexity}
                />

                <FormInput
                  type="text"
                  name="numberOfPlayers"
                  label={"Number of Players: "}
                  onChange={handleChange}
                  placeholder="2-4"
                  value={product.numberOfPlayers}
                />
                <FormInput
                  type="number"
                  name="rating"
                  label={"Rating: "}
                  onChange={handleChange}
                  placeholder="3.5"
                  value={product.rating}
                />

                <FormInput
                  type="number"
                  name="gameTime"
                  label={"Game Time: "}
                  onChange={handleChange}
                  placeholder="120"
                  value={product.gameTime}
                />

                <div className="items-center justify-center w-full">
                  <label
                    htmlFor="description"
                    className="block text-color_text-light text-sm font-normal"
                  >
                    Description:
                  </label>
                  <textarea
                    id="description"
                    name="description"
                    className="w-96 rounded-md my-2 px-2 py-2 text-color_text-dark"
                    placeholder="Cool description..."
                    value={product.description}
                    onChange={handleChange}
                  />
                </div>
              </form>
            </div>
            <button
              disabled={
                product.name === "" ||
                product.price === 0 ||
                product.description === ""
              }
              onClick={handleSubmit}
              className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 w-full enabled:hover:bg-secondary-dark disabled:opacity-50"
            >
              Create
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default NewProductForm;