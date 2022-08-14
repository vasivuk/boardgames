import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UserService from "../services/UserService";
import Button from "./Button";

const RegisterForm = () => {
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const saveUser = (e) => {
    e.preventDefault();
    UserService.saveUser(user)
      .then((response) => {
        console.log(response.data);
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="w-full h-screen flex justify-center items-start mt-10">
      {/* Register form */}
      <div className="flex max-w-2xl shadow border-b mx-auto bg-primary-standard text-color_text-light rounded-md">
        <div className="px-8 py-8">
          <div className="font-thin text-2xl tracking-wider">
            <h1>Register</h1>
          </div>

          <div className="h-14 w-full my-4">
            <label
              htmlFor=""
              className="block text-color_text-light text-sm font-normal"
            >
              First Name
            </label>
            <input
              type="text"
              name="firstName"
              onChange={(e) => handleChange(e)}
              className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
            ></input>
          </div>

          <div className="h-14 w-full my-4">
            <label
              htmlFor=""
              className="block text-color_text-light text-sm font-normal"
            >
              Last Name
            </label>
            <input
              type="text"
              name="lastName"
              onChange={(e) => handleChange(e)}
              className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
            ></input>
          </div>

          <div className="h-14 w-full my-4">
            <label
              htmlFor=""
              className="block text-color_text-light text-sm font-normal"
            >
              Email
            </label>
            <input
              type="email"
              name="email"
              onChange={(e) => handleChange(e)}
              className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
            ></input>
          </div>

          <div className="h-14 w-full my-4">
            <label
              htmlFor=""
              className="block text-color_text-light text-sm font-normal"
            >
              Password
            </label>
            <input
              type="password"
              name="password"
              onChange={(e) => handleChange(e)}
              className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
            ></input>
          </div>

          <div className="h-14 w-full my-4 pt-4 space-x-4">
            <Button text={"Create Account"} operation={saveUser} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterForm;
