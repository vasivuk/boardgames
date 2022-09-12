import React, { useEffect, useState } from "react";
import ErrorMessage from "../../components/ui/ErrorMessage";
import FormInput from "../../components/form/FormInput";
import genericIcon from "../../images/generic-boardgame-icon.png";
import CategoriesModal from "./CategoriesModal";
import { MdRemoveCircle } from "react-icons/md";
import { Rating } from "@mui/material";
import axios from "../../api/axios";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import Button from "../../components/ui/Button";

const NewProductForm = () => {
  const NAME_REGEX = /^\w.{0,23}/;
  const PRICE_REGEX = /^[1-9][0-9]*$/;
  const COMPLEXITY_REGEX = /^[0-4]$|([0-4]\.[0-9]{1,2}$)/;
  const PLAYERS_REGEX = /^\d-\d{1,2}$|^\d{1,2}$/;
  const TIME_REGEX = /^[1-9][0-9]*$/;
  const STOCK_REGEX = /^0$|^[1-9][0-9]*$/;

  const axiosPrivate = useAxiosPrivate();

  const [categories, setCategories] = useState([]);

  const [selectedCategories, setSelectedCategories] = useState([]);

  const [searchParam, setSearchParam] = useState("");

  useEffect(() => {
    axios
      .get(`/categories/name?search=${searchParam}`)
      .then((response) => setCategories(response.data))
      .catch(() => setCategories([]));
  }, [searchParam]);

  const [product, setProduct] = useState({
    name: "",
    description: "",
    price: "",
    imageUrl: "",
    complexity: "",
    numberOfPlayers: "",
    gameTime: "",
    rating: 0,
    stockQuantity: "",
  });

  const [validName, setValidName] = useState(false);
  const [validPrice, setValidPrice] = useState(false);
  const [validComplexity, setValidComplexity] = useState(false);
  const [validNumOfPlayers, setValidNumOfPlayers] = useState(false);
  const [validGameTime, setValidGameTime] = useState(false);
  const [validStockQuantity, setValidStockQuantity] = useState(false);

  useEffect(() => {
    setValidName(NAME_REGEX.test(product.name));
  }, [product.name]);

  useEffect(() => {
    setValidPrice(PRICE_REGEX.test(product.price));
  }, [product.price]);

  useEffect(() => {
    setValidComplexity(COMPLEXITY_REGEX.test(product.complexity));
  }, [product.complexity]);

  useEffect(() => {
    setValidNumOfPlayers(PLAYERS_REGEX.test(product.numberOfPlayers));
  }, [product.numberOfPlayers]);

  useEffect(() => {
    setValidGameTime(TIME_REGEX.test(product.gameTime));
  }, [product.gameTime]);

  useEffect(() => {
    setValidStockQuantity(STOCK_REGEX.test(product.stockQuantity));
  }, [product.stockQuantity]);

  const addCategory = function (category) {
    if (selectedCategories.includes(category)) {
      console.log("Category already added");
    } else {
      setSelectedCategories((prevSelectedCategories) => [
        ...prevSelectedCategories,
        category,
      ]);
    }
  };

  const removeCategory = function (category) {
    if (selectedCategories.includes(category)) {
      setSelectedCategories(
        selectedCategories.filter((c) => c.id !== category.id)
      );
    } else {
      console.log("Category is not in the list");
    }
  };

  const [errorMessage, setErrorMessage] = useState("");
  const [success, setSuccess] = useState(false);

  //Get all categories when page loads
  useEffect(() => {
    axios.get("/categories").then((response) => {
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

  // On submit
  function handleSubmit(e) {
    e.preventDefault();
    if (
      !validName ||
      product.description === "" ||
      !validPrice ||
      !validComplexity ||
      !validNumOfPlayers ||
      !validGameTime ||
      !validPrice ||
      !validStockQuantity
    ) {
      setErrorMessage("Invalid product data, a field is invalid");
      return;
    }
    const productWithCategories = {
      ...product,
      categories: selectedCategories,
    };

    console.log(productWithCategories);
    axiosPrivate
      .post("/products/create", product)
      .then((response) => {
        console.log(response?.data);
        setSuccess(true);
        setProduct({
          name: "",
          description: "",
          price: "",
          imageUrl: "",
          complexity: "",
          numberOfPlayers: "",
          gameTime: "",
          rating: 0,
          stockQuantity: "",
        });
        setSelectedCategories([]);
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
                {/* Image */}
                <div className="h-96">
                  <img
                    src={product.imageUrl || genericIcon}
                    alt=""
                    className="max-h-96 rounded-lg border-primary-dark border"
                  />
                </div>
                <FormInput
                  type="text"
                  name="imageUrl"
                  label={"Image source: "}
                  onChange={handleChange}
                  placeholder="https://localhost:8080/[somegame.jpg]"
                  value={product.imageUrl}
                />
                {/* Categories */}
                <div>
                  <div className="flex mt-3 py-2 justify-between items-center border-b border-primary-dark">
                    <h3 className="">Categories</h3>
                    <CategoriesModal
                      categories={categories}
                      alreadyAddedCategories={selectedCategories}
                      addCategory={addCategory}
                      searchParam={searchParam}
                      setSearchParam={setSearchParam}
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
                {/* Name */}
                <FormInput
                  type="text"
                  name="name"
                  label={"Name: "}
                  onChange={handleChange}
                  placeholder="New Product"
                  value={product.name}
                  valid={validName}
                  validMsg="Name must be an not empty string"
                  required
                />

                {/* Price */}
                <FormInput
                  type="text"
                  name="price"
                  label={"Price (In Euros): "}
                  onChange={handleChange}
                  placeholder="50"
                  value={product.price}
                  valid={validPrice}
                  validMsg="Price must be a positive number."
                  required
                />

                {/* Complexity */}
                <FormInput
                  type="text"
                  name="complexity"
                  label={"Complexity: "}
                  onChange={handleChange}
                  placeholder="2.4"
                  value={product.complexity}
                  valid={validComplexity}
                  validMsg="Complexity must be a number between 0 and 4.99 with maximum two decimal spots"
                />

                {/* Number of Players */}
                <FormInput
                  type="text"
                  name="numberOfPlayers"
                  label={"Number of Players: "}
                  onChange={handleChange}
                  placeholder="2-4"
                  value={product.numberOfPlayers}
                  valid={validNumOfPlayers}
                  validMsg={
                    "Number of players must be a number or a range (eg. 2-5)"
                  }
                />

                {/* Quantity in stock */}
                <FormInput
                  type="text"
                  name="stockQuantity"
                  label={"Stock quantity: "}
                  onChange={handleChange}
                  placeholder="10"
                  value={product.stockQuantity}
                  valid={validStockQuantity}
                  validMsg={"Field must be a valid number"}
                />

                {/* Rating */}
                <p className="text-sm">Rating:</p>
                <div className="flex items-center">
                  <Rating
                    size="large"
                    precision={0.5}
                    name="rating"
                    defaultValue={product.rating}
                    value={product.rating}
                    onChange={handleChange}
                  />{" "}
                  <span className="text-sm">({product.rating})</span>
                </div>
                <FormInput
                  type="text"
                  name="gameTime"
                  label={"Game Time: "}
                  onChange={handleChange}
                  placeholder="120"
                  value={product.gameTime}
                  valid={validGameTime}
                  validMsg="Game time must be a positive number, represents minutes"
                />

                {/* Description */}
                <div className="items-center justify-center w-full">
                  <label
                    htmlFor="description"
                    className="block text-color_text-light text-sm font-normal"
                  >
                    Description: <span className="text-red-600">*</span>
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
            <div className="py-2">
              <Button
                disabled={
                  !validName ||
                  !validPrice ||
                  product.description === "" ||
                  !validComplexity ||
                  !validNumOfPlayers ||
                  !validGameTime ||
                  !validStockQuantity
                }
                onClick={handleSubmit}
                text="Create"
                dark
                stretch
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default NewProductForm;
