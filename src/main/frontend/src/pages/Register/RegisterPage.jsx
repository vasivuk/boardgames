import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import UserService from "../../services/UserService";
import ErrorMessage from "../../components/ui/ErrorMessage";

const RegisterForm = () => {
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [success, setSuccess] = useState(false);

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
    UserService.saveUser(user)
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
    <div className="w-full h-screen flex justify-center items-start mt-10">
      {/* Register form */}
      <div className="flex max-w-2xl shadow border-b mx-auto bg-primary-standard text-color_text-light rounded-md p-8">
        {success ? (
          <div>
            <h1 className="font-semibold">Account successfully created!</h1>
            <Link to={"../login"}>
              <p className="underline hover:text-yellow-400">To Login Page</p>
            </Link>
          </div>
        ) : (
          <div>
            {errorMessage && <ErrorMessage message={errorMessage} />}
            <div className="font-thin text-2xl tracking-wider mt-3">
              <h1>Register</h1>
            </div>

            <div className="h-14 w-full my-4">
              <label
                htmlFor="firstName"
                className="block text-color_text-light text-sm font-normal"
              >
                First Name
              </label>
              <input
                type="text"
                name="firstName"
                id="firstName"
                onChange={(e) => handleChange(e)}
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
              ></input>
            </div>

            <div className="h-14 w-full my-4">
              <label
                htmlFor="lastName"
                className="block text-color_text-light text-sm font-normal"
              >
                Last Name
              </label>
              <input
                type="text"
                name="lastName"
                id="lastName"
                onChange={(e) => handleChange(e)}
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
              ></input>
            </div>

            <div className="h-14 w-full my-4">
              <label
                htmlFor="email"
                className="block text-color_text-light text-sm font-normal"
              >
                Email
              </label>
              <input
                type="email"
                name="email"
                id="email"
                onChange={(e) => handleChange(e)}
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
              ></input>
            </div>

            <div className="h-14 w-full my-4">
              <label
                htmlFor="password"
                className="block text-color_text-light text-sm font-normal"
              >
                Password
              </label>
              <input
                type="password"
                name="password"
                id="password"
                onChange={(e) => handleChange(e)}
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
              ></input>
            </div>

            <div className="h-14 w-full my-4 pt-4 space-x-4">
              <button
                disabled={
                  user.firstName === "" ||
                  user.lastName === "" ||
                  user.email === "" ||
                  user.password === ""
                }
                onClick={handleSubmit}
                className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 px-6 enabled:hover:bg-secondary-dark disabled:opacity-50"
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
