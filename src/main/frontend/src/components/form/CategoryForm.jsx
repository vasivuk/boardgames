import React from "react";
import Button from "../ui/Button";

const CategoryForm = ({ category, handleChange, handleSubmit, btnText }) => {
  return (
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
      <div className="pt-5">
        <Button
          disabled={category.name === "" || category.description === ""}
          onClick={handleSubmit}
          text={btnText}
          dark
          stretch
        />
      </div>
    </form>
  );
};

export default CategoryForm;
