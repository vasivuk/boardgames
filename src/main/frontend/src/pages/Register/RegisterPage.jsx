import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import axios from "../../api/axios";
import FormInput from "../../components/form/FormInput";
import ErrorMessage from "../../components/ui/ErrorMessage";

const RegisterForm = () => {
  const [countries, setCountries] = useState([]);

  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    country: "",
    city: "",
    address: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [success, setSuccess] = useState(false);

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

  useEffect(() => {
    setErrorMessage("");
  }, [user.email, user.firstName, user.lastName, user.password]);

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (
      user.firstName === "" ||
      user.lastName === "" ||
      user.email === "" ||
      user.password === ""
    ) {
      setErrorMessage("Invalid user data, a field is empty");
      return;
    }
    axios
      .post("/register", user)
      .then((response) => {
        console.log(response.data);
        setSuccess(true);
      })
      .catch((error) => {
        console.log(error);
        if (!error?.response.status) {
          setErrorMessage("No Server Response");
        } else if (error.response?.status === 409) {
          setErrorMessage("Email Already Taken");
        } else {
          setErrorMessage("Registration Failed");
        }
      });
  };

  return (
    <div className="w-full min-h-screen flex justify-center items-start mt-10">
      {/* Register form */}
      <div className="flex max-w-2xl w-1/2 shadow border-b mx-auto bg-primary-standard text-color_text-light rounded-md p-8 lg:w-1/3">
        {success ? (
          <div className="w-full">
            <h1 className="font-semibold">Account successfully created!</h1>
            <Link to={"../login"}>
              <p className="underline hover:text-yellow-400">To Login Page</p>
            </Link>
          </div>
        ) : (
          <div className="w-full">
            {errorMessage && <ErrorMessage message={errorMessage} />}
            <div className="font-thin text-2xl tracking-wider mt-3">
              <h1>Register</h1>
            </div>

            <FormInput
              label="First Name"
              name="firstName"
              onChange={handleChange}
              placeholder="John"
              type="text"
              value={user.firstName}
              required
            />

            <FormInput
              label="Last Name"
              name="lastName"
              onChange={handleChange}
              placeholder="Smith"
              type="text"
              value={user.lastName}
              required
            />

            <FormInput
              label="Email"
              name="email"
              onChange={handleChange}
              placeholder="example@email.com"
              type="email"
              value={user.email}
              required
            />

            <FormInput
              label="Password"
              name="password"
              onChange={handleChange}
              type="password"
              value={user.password}
              required
            />

            <label htmlFor="country" className="block text-sm font-normal">
              Country
            </label>
            <select
              id="country"
              className="h-9 w-full my-1 px-2 py-2 text-color_text-dark rounded"
              value={user.country}
              onChange={handleChange}
              name="country"
            >
              {countriesElements}
            </select>

            <FormInput
              label="City"
              name="city"
              onChange={handleChange}
              placeholder="Belgrade"
              type="text"
              value={user.city}
            />

            <FormInput
              label="Address"
              name="address"
              onChange={handleChange}
              placeholder="Majke Jevrosime 12"
              type="text"
              value={user.address}
            />

            <div className="h-14 w-full my-4 pt-4 space-x-4">
              <button
                disabled={
                  user.firstName === "" ||
                  user.lastName === "" ||
                  user.email === "" ||
                  user.password === ""
                }
                onClick={handleSubmit}
                className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 px-6 enabled:hover:bg-secondary-standard disabled:opacity-50"
              >
                Create Account
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default RegisterForm;
