import React from "react";
import { Slider } from "@mui/material";
import CategoriesModal from "./CategoriesModal";
import { MdRemoveCircle } from "react-icons/md";

const FilterSection = (props) => {
  return (
    <div className="col-span-1 p-5 border-r flex flex-col gap-3 mr-auto">
      <h2 className="text-xl uppercase font-bold p-2 border-b mb-2">Filter</h2>
      <div className="border p-4">
        <h3 className="text-lg">Price</h3>
        <Slider
          value={props.price}
          onChange={props.handlePriceChange}
          onChangeCommitted={props.sendSignal}
          min={0}
          max={200}
        />
        <div className="flex items-center justify-between">
          <div>
            <input
              type="text"
              placeholder="min"
              className="border w-10 text-center"
              value={props.price[0]}
              readOnly
            />
            <span>€</span>
          </div>
          <div>
            <input
              type="text"
              placeholder="max"
              className="border w-10 text-center"
              value={props.price[1]}
              readOnly
            />
            <span>€</span>
          </div>
        </div>
      </div>

      <div className="border p-4">
        <h3 className="text-lg">Categories</h3>
        <CategoriesModal
          categories={props.categories}
          alreadyAddedCategories={props.selectedCategories}
          addCategory={props.addCategory}
          searchParam={props.searchParam}
          setSearchParam={props.setSearchParam}
        />
        {props.selectedCategories.map((category) => (
          <div
            key={category?.id}
            className="border-b p-2 flex justify-between items-center"
          >
            <div>
              <p className="text-sm">{category.name}</p>
            </div>
            <div
              className="text-2xl text-white hover:text-red-500"
              onClick={() => props.removeCategory(category)}
            >
              <MdRemoveCircle />
            </div>
          </div>
        ))}
      </div>

      <div className="border p-4">
        <h3 className="text-lg">Game Time</h3>
        <Slider
          value={props.gameTime}
          onChange={props.handleGameTimeChange}
          onChangeCommitted={props.sendSignal}
          min={0}
          max={300}
        />
        <div className="flex items-center justify-between">
          <div>
            <input
              type="text"
              placeholder="min"
              className="border w-10 text-center"
              value={props.gameTime[0]}
              readOnly
            />
            <span>min</span>
          </div>
          <div>
            <input
              type="text"
              placeholder="max"
              className="border w-10 text-center"
              value={props.gameTime[1]}
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
