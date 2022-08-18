import React from "react";
import { useState } from "react";
import CategoryModalItem from "./CategoryModalItem";

const CategoriesModal = ({
  categories,
  alreadyAddedCategories,
  addCategory,
  searchParam,
  setSearchParam,
}) => {
  const [showModal, setShowModal] = React.useState(false);

  const [newCategories, setNewCategories] = useState([]);
  function toggleSelected(newCategory) {
    if (newCategories.includes(newCategory)) {
      setNewCategories(
        newCategories.filter((category) => category?.id !== newCategory?.id)
      );
    } else {
      setNewCategories((prevCategories) => [...prevCategories, newCategory]);
    }
  }

  const categoriesItems = categories
    .filter(
      (category) =>
        !alreadyAddedCategories.some(
          (element) => JSON.stringify(category) === JSON.stringify(element)
        )
    )
    .map((category) => (
      <CategoryModalItem
        key={category.id}
        category={category}
        isSelected={newCategories.some((ctg) => ctg.id === category.id)}
        handleClick={toggleSelected}
      />
    ));

  function handleSaveChanges() {
    newCategories.map((category) => addCategory(category));
    setShowModal(false);
  }

  function handleChange(e) {
    setSearchParam(e.target.value);
  }

  return (
    <>
      <button
        className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 px-5 enabled:hover:bg-secondary-dark disabled:opacity-50"
        onClick={() => setShowModal(true)}
      >
        Add Categories
      </button>
      {showModal ? (
        <>
          <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="relative my-6 mx-auto w-1/2">
              {/*content*/}
              <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-neutral-200 outline-none focus:outline-none">
                {/*header*/}
                <input
                  type="text"
                  placeholder="Search Categories..."
                  onChange={handleChange}
                  value={searchParam}
                  className="flex items-start justify-between p-4 border-b border-solid text-color_text-dark border-slate-200 rounded-t focus:outline-none"
                ></input>
                {/*body*/}
                <div className="relative flex-auto min-w-2">
                  <ul className="flex flex-col max-h-96 overflow-y-scroll">
                    {categoriesItems}
                  </ul>
                </div>
                {/*footer*/}
                <div className="flex items-center justify-between p-6 border-t rounded-b">
                  <button
                    className="bg-rose-500 text-white font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={() => setShowModal(false)}
                  >
                    Close
                  </button>
                  <button
                    className="bg-primary-light text-white font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={() => handleSaveChanges()}
                  >
                    Save Changes
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
        </>
      ) : null}
    </>
  );
};

export default CategoriesModal;
