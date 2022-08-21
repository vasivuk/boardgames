import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { AuthProvider } from "./context/AuthProvider";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { CookiesProvider } from "react-cookie";

const theme = createTheme({
  palette: {
    primary: {
      main: "#4d8b4f",
    },
    secondary: {
      main: "#fbf9ed",
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <CookiesProvider>
        <ThemeProvider theme={theme}>
          <AuthProvider>
            <Routes>
              <Route path="/*" element={<App />} />
            </Routes>
          </AuthProvider>
        </ThemeProvider>
      </CookiesProvider>
    </BrowserRouter>
  </React.StrictMode>
);
