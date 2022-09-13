import React from "react";
import {
  useLocation,
  Navigate,
  Outlet,
  useOutletContext,
} from "react-router-dom";
import useAuth from "../hooks/useAuth";

const RequireAuth = ({ allowedRole }) => {
  const { auth } = useAuth();
  const [cart, setCart] = useOutletContext();
  const location = useLocation();
  return (allowedRole && auth?.role === allowedRole) ||
    (!allowedRole && auth?.accessToken) ? (
    <Outlet context={[cart, setCart]} />
  ) : auth?.accessToken ? (
    <Navigate to="/unauthorized" state={{ from: location }} replace />
  ) : (
    <Navigate to="/login" state={{ from: location }} replace />
  );
};

export default RequireAuth;
