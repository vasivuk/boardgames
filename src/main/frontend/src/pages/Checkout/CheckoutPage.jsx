import React, { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";
import axios from "../../api/axios";
import FormInput from "../../components/form/FormInput";
import Button from "../../components/ui/Button";
import useAuth from "../../hooks/useAuth";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const CheckoutPage = () => {
  const [cart, setCart] = useOutletContext();
  const { auth } = useAuth();
  const [total, setTotal] = useState(0);
  const [user, setUser] = useState({});
  const [userDetails, setUserDetails] = useState({
    firstName: "",
    lastName: "",
    email: "",
    country: "",
    city: "",
    address: "",
  });

  const [countries, setCountries] = useState([]);

  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    setTotal(
      cart.reduce((prevValue, cartItem) => prevValue + cartItem?.subTotal, 0)
    );
  }, [cart]);

  //Get countries
  useEffect(() => {
    axios
      .get(
        `https://restcountries.com/v3.1/region/europe
    `
      )
      .then((response) => {
        setCountries(
          response?.data.map((dataEntry) => dataEntry?.name?.common)
        );
      })
      .catch((error) => console.log(error));
  }, []);

  const countriesElements = countries.map((country) => (
    <option className="w-full text-black" key={country} value={country}>
      {country}
    </option>
  ));

  //Get logged in user data
  useEffect(() => {
    axiosPrivate
      .get(`/users/user?email=${auth?.email}`)
      .then((response) => {
        setUser(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  useEffect(() => {
    setUserDetails((prev) => ({
      ...prev,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      city: user.city,
      country: user.country,
      address: user.address,
    }));
  }, [user]);

  const handleChange = (e) => {
    const value = e.target.value;
    setUserDetails({ ...userDetails, [e.target.name]: value });
  };

  const orderItems = cart.map((item) => (
    <div
      key={item.product.id}
      className="flex justify-between gap-10 py-5 border-b"
    >
      <p>
        {item?.product?.name} x {item?.quantity}
      </p>
      <p>{item?.subTotal} EUR</p>
    </div>
  ));

  function handleOrderSubmit() {
    const order = { orderItems: cart, total, userDetails, user };
    axiosPrivate
      .post("/orders", order)
      .then((response) => {
        console.log(response);
        alert("Order submitted!");
        setCart([]);
      })
      .catch((error) => console.log(error));
  }

  return (
    <div className="flex justify-center bg-neutral-100">
      <div className="flex flex-col items-center w-3/4 min-h-screen shadow-xl bg-white p-10">
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
                value={userDetails.firstName}
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
                value={userDetails.lastName}
                required={true}
              />
            </div>

            {/* Country */}
            <label
              htmlFor="country"
              className="block text-sm text-neutral-700 font-normal"
            >
              Country<span className="text-red-500"> *</span>
            </label>
            <select
              id="country"
              className="h-9 w-full my-1 px-2 text-color_text-dark border border-neutral-400 rounded"
              value={userDetails.country}
              onChange={handleChange}
              name="country"
            >
              {countriesElements}
            </select>
            <FormInput
              label="City"
              lblClassName="text-neutral-700"
              inputClassName="border-neutral-400 border"
              name="city"
              onChange={handleChange}
              placeholder="Beograd"
              type="text"
              value={userDetails.city}
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
              value={userDetails.address}
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
              value={userDetails.email}
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
              <Button
                disabled={
                  total <= 0 ||
                  userDetails.firstName === "" ||
                  userDetails.lastName === "" ||
                  userDetails.email === "" ||
                  userDetails.country === "" ||
                  userDetails.city === "" ||
                  userDetails.address === ""
                }
                onClick={handleOrderSubmit}
                text="Place Order"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
