/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect, useState } from "react";
import ErrorMessage from "../../components/ui/ErrorMessage";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import ProductForm from "../../components/form/ProductForm";

const NewProductForm = () => {
  const axiosPrivate = useAxiosPrivate();

  const [validForm, setValidForm] = useState(false);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [image, setImage] = useState({});
  const [product, setProduct] = useState({
    name: "",
    description: "",
    price: "",
    imageUrl: "",
    complexity: "",
    numberOfPlayers: "",
    gameTime: "",
    rating: 0,
    stockQuantity: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [success, setSuccess] = useState(false);

  //Error message
  useEffect(() => {
    setErrorMessage("");
  }, [product]);

  //On change in field
  const handleChange = (e) => {
    const value = e.target.value;
    setProduct({ ...product, [e.target.name]: value });
  };

  // On submit
  function handleSubmit(e) {
    e.preventDefault();
    if (!validForm) {
      setErrorMessage("Invalid product data, a field is invalid");
      return;
    }
    const imageData = new FormData();
    imageData.append("image", image);

    //Submit image first
    axiosPrivate
      .post("/images/save", imageData)
      .then((response) => {
        //Set image url product
        const productWithCategories = {
          ...product,
          imageUrl: response?.data,
          categories: selectedCategories,
        };
        //Save product
        axiosPrivate
          .post("/products/create", productWithCategories)
          .then((response) => {
            console.log(response?.data);
            setSuccess(true);
            setProduct({
              name: "",
              description: "",
              price: "",
              imageUrl: "",
              complexity: "",
              numberOfPlayers: "",
              gameTime: "",
              rating: 0,
              stockQuantity: "",
            });
            setSelectedCategories([]);
          })
          .catch((error) => {
            console.log(error);
            if (!error?.response.status) {
              setErrorMessage("No Server Response");
            } else if (error.response?.status === 409) {
              setErrorMessage("Product with supplied name already exists");
            } else if (error.response?.status === 403) {
              setErrorMessage("Unauthorized Action");
            } else {
              setErrorMessage("Can't create product");
            }
          });
      })
      .catch((error) => console.log(error));
  }
  return (
    <div className="w-full flex justify-center mx-auto items-start my-10">
      <div className="shadow border-b bg-primary-standard text-color_text-light rounded-md p-8">
        {errorMessage && <ErrorMessage message={errorMessage} />}
        <h1 className="font-thin text-2xl tracking-wider mb-4 pb-2 border-b-primary-dark border-b-2">
          New Product
        </h1>
        {success ? (
          <div>
            <h1 className="font-semibold">Product successfully created!</h1>
            <button
              className="underline hover:text-yellow-400"
              onClick={() => setSuccess(false)}
            >
              Create more products
            </button>
          </div>
        ) : (
          <ProductForm
            onSubmit={handleSubmit}
            onChange={handleChange}
            product={product}
            setValidForm={setValidForm}
            setImage={setImage}
            selectedCategories={selectedCategories}
            setSelectedCategories={setSelectedCategories}
            btnText="Create"
          />
        )}
      </div>
    </div>
  );
};

export default NewProductForm;
