import React from "react";

const CartItem = ({ item, handleDelete }) => {
  return (
    <tr>
      <td className="p-2">
        <div className="flex justify-center">
          <button onClick={() => handleDelete(item)}>
            <svg
              className="w-8 h-8 hover:text-red-600 rounded-full hover:bg-gray-100 p-1"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
              ></path>
            </svg>
          </button>
        </div>
      </td>
      <td className="p-2">
        <img src={item?.product?.imageUrl} alt="" className="w-20" />
      </td>
      <td className="p-2">
        <div className="font-medium text-gray-800">{item?.product?.name}</div>
      </td>
      <td className="p-2">
        <div className="text-left">{item?.count}</div>
      </td>
      <td className="p-2">
        <div className="text-left font-medium text-green-500">
          {item?.subTotal} EUR
        </div>
      </td>
    </tr>
  );
};

export default CartItem;
