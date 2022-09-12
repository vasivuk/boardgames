import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link, useOutletContext } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import CartItem from "./CartItem";

const Cart = () => {
  const [cart, setCart] = useOutletContext();
  const [total, setTotal] = useState(0);

  const { auth } = useAuth();

  useEffect(() => {
    if (cart.length > 0) {
      setTotal(
        cart.reduce((prevValue, cartItem) => prevValue + cartItem?.subTotal, 0)
      );
    } else {
      setTotal(0);
    }
  }, [cart]);

  const cartItems = cart.map((item) => (
    <CartItem key={item?.product?.id} item={item} handleDelete={handleDelete} />
  ));

  function handleDelete(item) {
    setCart((currentCart) =>
      currentCart.filter((currentItem) => currentItem !== item)
    );
  }

  return (
    <div className="flex flex-col items-center bg-neutral-100 w-full">
      <div className="flex flex-col items-center min-h-screen w-3/4 bg-white shadow-xl p-10">
        <h1 className="text-3xl uppercase p-4 font-bold text-neutral-600">
          Cart
        </h1>
        <div className="w-4/5">
          <table className="table-auto w-full">
            <thead className="text-xs font-semibold uppercase text-gray-400 bg-gray-50">
              <tr>
                <th className="w-24"></th>
                <th className="w-28"></th>
                <th className="p-2 ">
                  <div className="font-semibold text-left">Product Name</div>
                </th>
                <th className="p-2">
                  <div className="font-semibold text-left">Quantity</div>
                </th>
                <th className="p-2">
                  <div className="font-semibold text-left">Subtotal</div>
                </th>
              </tr>
            </thead>

            <tbody className="text-sm divide-y divide-gray-100">
              {cartItems}
            </tbody>
          </table>
          <div className="p-5 w-full flex items-center gap-4">
            <h2 className="text-xl text-neutral-600 font-semibold">Total:</h2>
            <p className="text-xl text-green-600 font-bold">{total} EUR</p>
          </div>
          {auth?.accessToken ? (
            <Link to="/checkout">
              <button
                disabled={total <= 0}
                className="p-3 bg-primary-standard text-white font-semibold text-lg rounded-xl disabled:opacity-50"
              >
                Proceed to Checkout
              </button>
            </Link>
          ) : (
            <Link
              to={"/login"}
              className="p-3 bg-primary-standard text-white font-semibold text-lg rounded-xl hover:bg-primary-light"
            >
              Login to Proceed to Checkout
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default Cart;
