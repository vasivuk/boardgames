import { Rating } from "@mui/material";
import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { MdRemoveCircle } from "react-icons/md";
import axios from "../../api/axios";
import CategoriesModal from "../../pages/Product/CategoriesModal";
import FormInput from "./FormInput";
import genericIcon from "../../images/generic-boardgame-icon.png";
import Button from "../ui/Button";

const ProductForm = ({
  onSubmit,
  onChange,
  product,
  setValidForm,
  setImage,
  selectedCategories,
  setSelectedCategories,
  btnText,
}) => {
  const NAME_REGEX = /^\w.{0,23}/;
  const PRICE_REGEX = /^[1-9][0-9]*$/;
  const COMPLEXITY_REGEX = /^[0-4]$|([0-4]\.[0-9]{1,2}$)/;
  const PLAYERS_REGEX = /^\d-\d{1,2}$|^\d{1,2}$/;
  const TIME_REGEX = /^[1-9][0-9]*$/;
  const STOCK_REGEX = /^0$|^[1-9][0-9]*$/;

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

  useEffect(() => {
    if (
      validComplexity &&
      validGameTime &&
      validName &&
      validNumOfPlayers &&
      validPrice &&
      validStockQuantity
    ) {
      setValidForm(true);
    }
  }, [
    validComplexity,
    validGameTime,
    validName,
    validNumOfPlayers,
    validPrice,
    validStockQuantity,
  ]);

  const [categories, setCategories] = useState([]);

  //Get all categories when page loads
  useEffect(() => {
    axios.get("/categories").then((response) => {
      setCategories(response?.data);
    });
  }, []);

  const [searchParam, setSearchParam] = useState("");

  useEffect(() => {
    axios
      .get(`/categories/name?search=${searchParam}`)
      .then((response) => setCategories(response.data))
      .catch(() => setCategories([]));
  }, [searchParam]);

  const [imagePreview, setImagePreview] = useState();

  function handleImageUpload(e) {
    setImagePreview(URL.createObjectURL(e.target.files[0]));
    setImage(e.target.files[0]);
  }

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

  return (
    <div>
      <div className="flex flex-row gap-5">
        <div className="w-96 justify-self-center rounded-xl">
          {/* Image */}
          <div className="h-96">
            <img
              src={imagePreview || product.imageUrl || genericIcon}
              alt=""
              className="max-h-96 rounded-lg border-primary-dark border"
            />
          </div>
          <input
            type="file"
            name="image"
            accept="image/*"
            onChange={handleImageUpload}
            className="p-2"
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
            onChange={onChange}
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
            onChange={onChange}
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
            onChange={onChange}
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
            onChange={onChange}
            placeholder="2-4"
            value={product.numberOfPlayers}
            valid={validNumOfPlayers}
            validMsg={"Number of players must be a number or a range (eg. 2-5)"}
          />

          {/* Quantity in stock */}
          <FormInput
            type="text"
            name="stockQuantity"
            label={"Stock quantity: "}
            onChange={onChange}
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
              onChange={onChange}
            />{" "}
            <span className="text-sm">({product.rating})</span>
          </div>
          <FormInput
            type="text"
            name="gameTime"
            label={"Game Time: "}
            onChange={onChange}
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
              onChange={onChange}
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
          onClick={onSubmit}
          text={btnText}
          dark
          stretch
        />
      </div>
    </div>
  );
};

export default ProductForm;
