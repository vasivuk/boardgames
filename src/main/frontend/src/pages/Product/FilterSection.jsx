import React, { useEffect, useState } from "react";
import { Slider } from "@mui/material";
import CategoriesModal from "./CategoriesModal";
import CategoryService from "../../services/CategoryService";
import { MdRemoveCircle } from "react-icons/md";

const FilterSection = () => {
  const [price, setPrice] = useState([0, 200]);
  const [gameTime, setGameTime] = useState([0, 300]);

  const [categories, setCategories] = useState([]);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [searchParam, setSearchParam] = useState("");

  useEffect(() => {
    CategoryService.findCategories(searchParam)
      .then((response) => setCategories(response.data))
      .catch(() => setCategories([]));
  }, [searchParam]);

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

  function handlePriceChange(e, newPrice) {
    setPrice(newPrice);
  }

  function handleGameTimeChange(e, newGameTime) {
    setGameTime(newGameTime);
  }

  return (
    <div className="p-5 border-r flex flex-col gap-3 w-1/4">
      <h2 className="text-xl uppercase font-bold p-2 border-b mb-2">Filter</h2>
      <div className="border p-4">
        <h3 className="text-lg">Price</h3>
        <Slider value={price} onChange={handlePriceChange} min={0} max={200} />
        <div className="flex items-center justify-between">
          <div>
            <input
              type="text"
              placeholder="min"
              className="border w-10 text-center"
              value={price[0]}
              readOnly
            />
            <span>€</span>
          </div>
          <div>
            <input
              type="text"
              placeholder="max"
              className="border w-10 text-center"
              value={price[1]}
              readOnly
            />
            <span>€</span>
          </div>
        </div>
      </div>

      <div className="border p-4">
        <h3 className="text-lg">Categories</h3>
        <CategoriesModal
          categories={categories}
          alreadyAddedCategories={selectedCategories}
          addCategory={addCategory}
          searchParam={searchParam}
          setSearchParam={setSearchParam}
        />
        {selectedCategories.map((category) => (
          <div
            key={category?.id}
            className="border-b p-2 flex justify-between items-center"
          >
            <div>
              <p className="text-sm">{category.name}</p>
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

      <div className="border p-4">
        <h3 className="text-lg">Game Time</h3>
        <Slider
          value={gameTime}
          onChange={handleGameTimeChange}
          min={0}
          max={300}
        />
        <div className="flex items-center justify-between">
          <div>
            <input
              type="text"
              placeholder="min"
              className="border w-10 text-center"
              value={gameTime[0]}
              readOnly
            />
            <span>min</span>
          </div>
          <div>
            <input
              type="text"
              placeholder="max"
              className="border w-10 text-center"
              value={gameTime[1]}
              readOnly
            />
            <span>min</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FilterSection;
