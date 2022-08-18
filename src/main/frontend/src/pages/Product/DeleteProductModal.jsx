import React from "react";
import { AiFillDelete } from "react-icons/ai";

const DeleteProductModal = ({ handleDelete }) => {
  const [showModal, setShowModal] = React.useState(false);

  return (
    <>
      <button
        className="text-xl text-neutral-700 hover:text-red-700 hover:cursor-pointer mx-2"
        onClick={() => setShowModal(true)}
      >
        <AiFillDelete />
      </button>
      {showModal ? (
        <>
          <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="my-6 mx-auto max-w-3xl">
              {/*content*/}
              <div className="rounded-lg shadow-lg flex flex-col bg-secondary-standard">
                <h1 className="text-2xl font-bold p-5 text-color_text-dark">
                  Are you sure you want to delete this product?
                </h1>
                {/*footer*/}
                <div className="flex items-center justify-between p-6 rounded-b">
                  <button
                    className="bg-neutral-300 text-color_text-dark font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={() => setShowModal(false)}
                  >
                    Cancel
                  </button>
                  <button
                    className="bg-rose-500 text-white font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={handleDelete}
                  >
                    Delete
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

export default DeleteProductModal;
