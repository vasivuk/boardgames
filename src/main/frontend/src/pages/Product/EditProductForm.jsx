import React, { useEffect, useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import ErrorMessage from "../../components/ui/ErrorMessage";
import axios from "../../api/axios";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import ProductForm from "../../components/form/ProductForm";

const EditProductForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
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

  //Get the product when form loads
  useEffect(() => {
    axios.get(`products/${id}`).then((response) => {
      setProduct(response.data);
      setSelectedCategories(response.data.categories);
    });
  }, []);

  const [errorMessage, setErrorMessage] = useState("");
  useEffect(() => {
    setErrorMessage("");
  }, [product]);

  const handleChange = (e) => {
    const value = e.target.value;
    setProduct({ ...product, [e.target.name]: value });
  };

  function handleSubmit(e) {
    e.preventDefault();
    if (!validForm || product.description === "") {
      setErrorMessage("Invalid product data, a field is invalid");
      return;
    }
    //If there has been a change in image
    if (image instanceof File) {
      console.log("If grana");
      const imageData = new FormData();
      imageData.append("image", image);

      axiosPrivate
        .post("/images/save", imageData)
        .then((response) => {
          //Set image url product
          const productWithCategories = {
            ...product,
            imageUrl: response?.data,
            categories: selectedCategories,
          };
          //Update product
          axiosPrivate
            .put(`/products/${productWithCategories.id}`, productWithCategories)
            .then((response) => {
              console.log(response?.data);
              setErrorMessage("");
              navigate(-1);
            })
            .catch((error) => {
              console.log(error);
              if (!error?.response.status) {
                setErrorMessage("No Server Response");
              } else if (error.response?.status === 409) {
                setErrorMessage("Product with supplied id doesn't exist");
              } else if (error.response?.status === 403) {
                setErrorMessage("Unauthorized Action");
                navigate("/login", {
                  state: { from: location },
                  replace: true,
                });
              } else {
                setErrorMessage("Can't create product");
              }
            });
        })
        .catch((error) => console.log(error));
      //No change in image, just update product
    } else {
      const productWithCategories = {
        ...product,
        categories: selectedCategories,
      };
      axiosPrivate
        .put(`/products/${productWithCategories.id}`, productWithCategories)
        .then((response) => {
          console.log(response?.data);
          setErrorMessage("");
          navigate(-1);
        })
        .catch((error) => {
          console.log(error);
          if (!error?.response.status) {
            setErrorMessage("No Server Response");
          } else if (error.response?.status === 409) {
            setErrorMessage("Product with supplied id doesn't exist");
          } else if (error.response?.status === 403) {
            setErrorMessage("Unauthorized Action");
            navigate("/login", { state: { from: location }, replace: true });
          } else {
            setErrorMessage("Can't create product");
          }
        });
    }
  }
  return (
    <div className="w-full flex justify-center mx-auto items-start my-10">
      <div className="shadow border-b bg-primary-standard text-color_text-light rounded-md p-8">
        {errorMessage && <ErrorMessage message={errorMessage} />}
        <h1 className="font-thin text-2xl tracking-wider mb-4 pb-2 border-b-primary-dark border-b-2">
          Edit Product
        </h1>
        <ProductForm
          onSubmit={handleSubmit}
          onChange={handleChange}
          product={product}
          setValidForm={setValidForm}
          setImage={setImage}
          selectedCategories={selectedCategories}
          setSelectedCategories={setSelectedCategories}
          btnText="Update"
        />
      </div>
    </div>
  );
};

export default EditProductForm;
