import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import UserService from "../services/UserService";
import Button from "./Button";

const LoginForm = () => {
  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const login = (e) => {
    e.preventDefault();
    UserService.login(user)
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
      {/* Login form */}
      <div className="flex max-w-2xl shadow border-b mx-auto bg-primary-standard text-color_text-light rounded-md">
        <div className="px-8 py-8">
          <div className="font-thin text-2xl tracking-wider">
            <h1>Login</h1>
          </div>

          <div className="items-center justify-center h-14 w-full my-4">
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
            <Button text="Login" operation={login} />
            <Link to={"/register"} className="hover:text-yellow-400">
              Create new account?
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
