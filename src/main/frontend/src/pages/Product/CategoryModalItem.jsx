import React from "react";

const CategoryModalItem = ({ category, isSelected, handleClick }) => {
  const selected = isSelected
    ? "bg-primary-light text-white"
    : "bg-neutral-200 text-neutral-700";

  return (
    <li
      onClick={() => handleClick(category)}
      className={
        "border-b border-neutral-300 p-4 hover:bg-primary-light hover:text-white " +
        selected
      }
    >
      <h3 className="text-lg font-semibold">{category.name}</h3>
      <p className="text-sm">{category.description}</p>
    </li>
  );
};

export default CategoryModalItem;
