import React, { useEffect, useState, useContext } from "react";
import AuthContext from "../../context/AuthProvider";
import { Link, useNavigate, useLocation } from "react-router-dom";
import ErrorMessage from "../../components/ui/ErrorMessage";
import axios from "../../api/axios";
import Button from "../../components/ui/Button";
import FormInput from "../../components/form/FormInput";

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
    const params = new URLSearchParams();
    params.append("email", user.email);
    params.append("password", user.password);
    axios
      .post("/login", params, { withCredentials: true })
      .then((response) => {
        console.log(response?.data);
        const accessToken = response?.data?.accessToken;
        console.log(response);
        //TODO: Get ADMIN role
        const email = user.email;
        const pass = user.password;
        const role = response?.data?.authority;
        setAuth({ email, pass, role, accessToken });
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
    <div className="w-full min-h-screen flex justify-center items-start mt-10">
      {/* Login form */}
      <div className="flex max-w-2xl border-b mx-auto bg-primary-standard text-color_text-light rounded-md w-96">
        <div className="p-8 w-full">
          {errorMessage && <ErrorMessage message={errorMessage} />}
          <div className="font-thin text-2xl tracking-wider py-3">
            <h1>Log In</h1>
          </div>
          <form className="w-full">
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

            <div className="py-5">
              <Button
                disabled={user.email === "" || user.password === ""}
                onClick={handleSubmit}
                text="Log In"
                dark
                stretch
              />
            </div>
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
