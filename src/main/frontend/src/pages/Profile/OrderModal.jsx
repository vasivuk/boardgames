import React from "react";
import { useState } from "react";

const OrderModal = ({ order }) => {
  const [showModal, setShowModal] = useState(false);
  console.log(order);

  const orderItemsElement = order?.orderItems.map((item) => (
    <div className="flex justify-between">
      <p>
        {item?.productName} x {item?.quantity}
      </p>
      <p>={item?.subTotal} EUR</p>
    </div>
  ));
  return (
    <>
      <tr
        key={order?.id}
        className="bg-neutral-200 text-color_text-dark border-b py-3 px-6 hover:bg-neutral-300"
        onClick={() => setShowModal(true)}
      >
        <td className="py-3 px-6">{order?.id}</td>
        <td className="py-3 px-6">{order?.dateSubmitted.substring(0, 10)}</td>
        <td className="py-3 px-6">{order?.totalPrice} EUR</td>
      </tr>
      {showModal && (
        <>
          <div className="justify-center text-neutral-700 items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="max-w-3xl">
              {/*content*/}
              <div className="rounded-lg  shadow-lg flex flex-col gap-3 bg-neutral-200 w-96">
                <h1 className="text-xl font-bold p-5 bg-primary-standard text-neutral-100">
                  Order ID: {order?.id}
                </h1>
                <div className="p-5 flex flex-col gap-2">
                  <h1 className="text-lg font-semibold text-neutral-700">
                    Date Submitted: {order?.dateSubmitted.substring(0, 10)}
                  </h1>
                  <h3 className="font-semibold">Order Items:</h3>
                  <div className="">{orderItemsElement}</div>
                  <h3 className="text-lg font-semibold text-neutral-700">
                    Total: {order?.totalPrice} EUR
                  </h3>
                </div>
                {/*footer*/}
                <div className="flex items-center justify-center p-6 rounded bg-">
                  <button
                    className="bg-neutral-300 text-color_text-dark font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={() => setShowModal(false)}
                  >
                    Exit
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
        </>
      )}
    </>
  );
};

export default OrderModal;
