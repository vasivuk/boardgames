/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          light: "#5e9960",
          standard: "#4d8b4f",
          dark: "#437a44",
        },

        secondary: {
          standard: "#fbf9ed",
          dark: "#f8efc6",
        },

        color_text: {
          light: "#fbf9ed",
          dark: "#414141",
        },
      },
    },
  },
  plugins: [],
};
