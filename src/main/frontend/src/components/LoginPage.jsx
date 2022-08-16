import React, { useEffect, useState, useContext } from "react";
import AuthContext from "../context/AuthProvider";
import { Link, useNavigate, useLocation } from "react-router-dom";
import UserService from "../services/UserService";
import ErrorMessage from "./ErrorMessage";

const LoginForm = () => {
  const { setAuth } = useContext(AuthContext);

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    setErrorMessage("");
  }, [user.email, user.password]);

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    UserService.login(user)
      .then((response) => {
        const accessToken = response?.data?.access_token;
        //TODO: Get ADMIN role
        const email = user.email;
        const pass = user.password;
        setAuth({ email, pass, accessToken });
        setUser({ email: "", password: "" });
        navigate(from, { replace: true });
      })
      .catch((error) => {
        console.log(error);
        if (!error?.response.status) {
          setErrorMessage("No Server Response");
        } else if (error.response?.status === 403) {
          setErrorMessage(
            "The email and password combination you provided isn't connected to an account."
          );
        } else {
          setErrorMessage("Sign in failed");
        }
      });
  };

  return (
    <div className="w-full h-screen flex justify-center items-start mt-10">
      {/* Login form */}
      <div className="flex max-w-2xl shadow border-b mx-auto bg-primary-standard text-color_text-light rounded-md">
        <div className="p-8">
          {errorMessage && <ErrorMessage message={errorMessage} />}
          <div className="font-thin text-2xl tracking-wider py-3">
            <h1>Log In</h1>
          </div>
          <form>
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
                autoComplete="off"
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
                value={user.email}
                required
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
                className="h-10 w-96 border mt-2 px-2 py-2 text-color_text-dark"
                type="password"
                name="password"
                onChange={(e) => handleChange(e)}
                value={user.password}
                required
              ></input>
            </div>

            <button
              disabled={user.email === "" || user.password === ""}
              onClick={handleSubmit}
              className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 my-5 w-full enabled:hover:bg-secondary-dark disabled:opacity-50"
            >
              Log In
            </button>
          </form>
          <p>Don't have an account?</p>
          <Link
            to={"/register"}
            className="font-semibold underline hover:text-yellow-400"
          >
            Sign up!
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
