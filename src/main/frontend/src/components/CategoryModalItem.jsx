import React from "react";
import { useState } from "react";

const CategoryModalItem = ({ category }) => {
  const [selected, setSelected] = useState(false);

  function handleClick() {
    setSelected((prevSelected) => !prevSelected);
    console.log(category.id);
  }
  return (
    <li
      onClick={handleClick}
      className={
        "border-b p-4 border-neutral-500 hover:bg-primary-light " +
        (selected ? " bg-primary-light" : " bg-neutral-700")
      }
    >
      <h3 className="text-lg font-semibold">{category.name}</h3>
      <p className="text-sm">{category.description}</p>
    </li>
  );
};

export default CategoryModalItem;
