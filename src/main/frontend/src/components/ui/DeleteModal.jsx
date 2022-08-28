import React from "react";
import { FaTrash } from "react-icons/fa";

const DeleteModal = ({ handleDelete, message }) => {
  const [showModal, setShowModal] = React.useState(false);

  function handleModal() {
    setShowModal(false);
    handleDelete();
  }

  return (
    <>
      <button
        className="text-xl opacity-25 hover:text-red-700 transition-opacity hover:opacity-100"
        onClick={() => setShowModal(true)}
      >
        <FaTrash />
      </button>
      {showModal ? (
        <>
          <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="my-6 mx-auto max-w-3xl">
              {/*content*/}
              <div className="rounded-lg shadow-lg flex flex-col bg-secondary-standard">
                <h1 className="text-2xl font-bold p-5 text-color_text-dark">
                  {message}
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
                    className="bg-red-500 text-white font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={handleModal}
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

export default DeleteModal;
