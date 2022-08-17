import React from "react";

const CategoryModalItem = ({ category, isSelected, handleClick }) => {
  const selected = isSelected ? "bg-primary-light" : "bg-neutral-700";

  return (
    <li
      onClick={() => handleClick(category)}
      className={
        "border-b p-4 border-neutral-500 hover:bg-primary-light " + selected
      }
    >
      <h3 className="text-lg font-semibold">{category.name}</h3>
      <p className="text-sm">{category.description}</p>
    </li>
  );
};

export default CategoryModalItem;
