import React, { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";
import FormInput from "../../components/form/FormInput";

const CheckoutPage = () => {
  const [cart, setCart] = useOutletContext();
  const [total, setTotal] = useState(0);
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    country: "",
    city: "",
    address: "",
  });

  useEffect(() => {
    setTotal(
      cart.reduce((prevValue, cartItem) => prevValue + cartItem?.subTotal, 0)
    );
  }, [cart]);

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const orderItems = cart.map((item) => (
    <div className="flex justify-between gap-10 py-5 border-b">
      <p>
        {item?.product?.name} x {item?.count}
      </p>
      <p>{item?.subTotal} EUR</p>
    </div>
  ));

  return (
    <div className="flex justify-center bg-neutral-100">
      <div className="flex flex-col items-center w-2/3 min-h-screen bg-white p-10">
        <h1 className="text-3xl text-neutral-600 font-bold py-4 my-10 w-full border-b-2 border-primary-dark uppercase">
          Checkout
        </h1>
        <div className="flex justify-between w-full px-5 gap-10">
          <div>
            <h2 className="text-xl font-bold p-2 border-b">Billing details</h2>
            <div className="flex gap-5">
              <FormInput
                label="First name"
                lblClassName="text-neutral-700"
                inputClassName="border-neutral-400 border"
                name="firstName"
                onChange={handleChange}
                placeholder="John"
                type="text"
                value={user.firstName}
                required={true}
              />
              <FormInput
                label="Last name"
                lblClassName="text-neutral-700"
                inputClassName="border-neutral-400 border"
                name="lastName"
                onChange={handleChange}
                placeholder="Smith"
                type="text"
                value={user.lastName}
                required={true}
              />
            </div>
            <FormInput
              label="Country"
              lblClassName="text-neutral-700"
              inputClassName="border-neutral-400 border"
              name="country"
              onChange={handleChange}
              placeholder="Serbia"
              type="text"
              value={user.country}
              required={true}
            />{" "}
            <FormInput
              label="City"
              lblClassName="text-neutral-700"
              inputClassName="border-neutral-400 border"
              name="city"
              onChange={handleChange}
              placeholder="Beograd"
              type="text"
              value={user.city}
              required={true}
            />
            <FormInput
              label="Street address"
              lblClassName="text-neutral-700"
              inputClassName="border-neutral-400 border"
              name="address"
              onChange={handleChange}
              placeholder="Majke Jevrosime 12"
              type="text"
              value={user.address}
              required={true}
            />
            <FormInput
              label="Email address"
              lblClassName="text-neutral-700"
              inputClassName="border-neutral-400 border"
              name="email"
              onChange={handleChange}
              placeholder="example@email.com"
              type="email"
              value={user.email}
              required={true}
            />
          </div>
          <div className="p-4 border-2">
            <h2 className="text-xl font-bold">Your order</h2>
            <div className="flex justify-between py-5 border-b font-semibold">
              <p>Item</p>
              <p>Subtotal</p>
            </div>
            {orderItems}
            <h3 className="text-lg font-semibold py-3">
              Total: <span className="text-green-700">{total} EUR</span>
            </h3>
            <div className="my-3">
              <button
                disabled={
                  total <= 0 ||
                  user.firstName === "" ||
                  user.lastName === "" ||
                  user.email === "" ||
                  user.country === "" ||
                  user.city === "" ||
                  user.address === ""
                }
                className="p-3 bg-primary-standard text-white font-semibold text-lg rounded-xl disabled:opacity-50"
              >
                Place Order
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
