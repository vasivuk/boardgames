import React from "react";

const CategoryModalItem = ({ category, isSelected, handleClick }) => {
  const selected = isSelected
    ? "bg-primary-light text-color_text-light"
    : "bg-secondary-standard";

  return (
    <li
      onClick={() => handleClick(category)}
      className={
        "border-b p-4 text-color_text-dark hover:bg-primary-light hover:text-color_text-light " +
        selected
      }
    >
      <h3 className="text-lg font-semibold">{category.name}</h3>
      <p className="text-sm">{category.description}</p>
    </li>
  );
};

export default CategoryModalItem;
