import React from "react";
import { useState, useEffect } from "react";
import { FaStar } from "react-icons/fa";
import { FiEdit } from "react-icons/fi";
import {
  useParams,
  useNavigate,
  useOutletContext,
  Link,
} from "react-router-dom";
import axios from "../../api/axios";
import Button from "../../components/ui/Button";
import DeleteModal from "../../components/ui/DeleteModal";
import useAuth from "../../hooks/useAuth";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const ProductForm = () => {
  const [cart, setCart] = useOutletContext();
  const [quantity, setQuantity] = useState(1);
  const [subTotal, setSubTotal] = useState();

  const [success, setSuccess] = useState(false);

  const axiosPrivate = useAxiosPrivate();
  let { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState({});

  const categoriesElement =
    product.categories &&
    product.categories.map((category) => (
      <li key={category.id} className="text-green-600 hover:text-yellow-500">
        <Link to={`/categories/${category.id}/${category.name}`}>
          {category.name}
        </Link>
      </li>
    ));

  const { auth } = useAuth();

  //Fetching the product
  useEffect(() => {
    axios
      .get(`products/${id}`)
      .then((response) => setProduct(response?.data))
      .catch((error) => console.log(error));
  }, []);

  //Change subtotal whenever quantity updates
  useEffect(() => {
    setSubTotal(product?.price * quantity);
  }, [product, quantity]);

  function handleDelete() {
    axiosPrivate
      .delete(`/products/${id}`)
      .then((response) => {
        console.log(response);
        navigate("/boardgames");
      })
      .catch((error) => console.log(error));
  }

  function handleIncrement() {
    quantity < product.stockQuantity &&
      setQuantity((prevQuantity) => prevQuantity + 1);
  }

  function handleDecrement() {
    quantity > 1 && setQuantity((prevQuantity) => prevQuantity - 1);
  }

  function handleAddToCart(e) {
    e.preventDefault();
    const cartItem = {
      product,
      quantity,
      subTotal,
    };
    //If the product is already in the cart
    if (
      cart.some((productInCart) => productInCart?.product?.id === product.id)
    ) {
      //Increment the quantity of that product
      setCart((prevCart) =>
        prevCart.map((prevItem) => {
          if (prevItem?.product?.id === product?.id) {
            return {
              ...prevItem,
              quantity: prevItem?.quantity + quantity,
              subTotal: prevItem?.subTotal + subTotal,
            };
          }
          return prevItem;
        })
      );
      //Add product to cart
    } else {
      setCart((prevCart) => [...prevCart, cartItem]);
    }
    setSuccess(true);
  }

  return (
    <div>
      <div className="w-full flex flex-col items-center bg-neutral-100">
        <div className="w-3/4 bg-white shadow-xl p-10">
          {/* Title Section */}
          <div className="p-5">
            <div className="flex items-center">
              <h1 className="text-3xl font-extrabold tracking-widest text-neutral-700">
                {product.name}
              </h1>
              {auth?.role === "ADMIN" && (
                <div className="flex gap-2">
                  <Link to={"./edit"}>
                    <div className="text-xl opacity-50 text-neutral-700 hover:text-primary-standard hover:cursor-pointer hover:opacity-100 mx-2">
                      <FiEdit />
                    </div>
                  </Link>
                  <DeleteModal
                    handleDelete={handleDelete}
                    message="Are you sure you want to delete this product?"
                  />
                </div>
              )}
            </div>
            <ul className="flex gap-3 border-b mt-4 font-semibold">
              {categoriesElement}
            </ul>
          </div>

          {/* Body */}
          <div className="flex flex-row gap-5 m-5">
            <img src={product.imageUrl} alt="" className="w-96 max-h-fit" />
            <div className="w-full border flex flex-col justify-between p-5">
              {/* Price */}
              <div className="flex flex-col items-center xl:flex-row">
                <h2 className="text-2xl font-semibold text-neutral-700">
                  Price:{" "}
                  <span className="text-green-500">{product.price} EUR</span>
                </h2>
                <div className="p-5 flex justify-center items-center ">
                  <div className="flex flex-col">
                    <button
                      className="w-7 h-5 bg-primary-dark rounded-tl border-b text-white flex items-center justify-center hover:bg-primary-light"
                      onClick={handleIncrement}
                    >
                      +
                    </button>
                    <button
                      className="w-7 h-5 bg-primary-dark rounded-bl text-white flex items-center justify-center hover:bg-primary-light"
                      onClick={handleDecrement}
                    >
                      -
                    </button>
                  </div>
                  <input
                    type="text"
                    className="border border-gray-400 w-10 h-10 text-center rounded-r mr-3"
                    value={quantity}
                    readOnly
                  />
                  <Button
                    disabled={product.stockQuantity <= 0}
                    onClick={handleAddToCart}
                    text="Add to Cart"
                  />
                </div>
              </div>

              {success && (
                <div className="relative bg-neutral-200 border border-neutral-400 p-2 transform transition-all duration-100 ease-in">
                  <h1 className="text-neutral-700">Product added to cart.</h1>
                  <button
                    onClick={() => setSuccess(false)}
                    className="absolute opacity-75 -top-3 -right-2 px-2 rounded-2xl bg-red-300 text-red-700 hover:opacity-100 border border-red-700"
                  >
                    X
                  </button>
                </div>
              )}
              <div>
                {product.stockQuantity > 0 ? (
                  <p className="py-3 font-bold text-green-500">
                    In stock: {product.stockQuantity}
                  </p>
                ) : (
                  <p className="py-3 text-red-600 font-bold">Out of stock</p>
                )}
                <table className="table-auto border w-full">
                  <thead className="p-5 text-lg bg-primary-standard">
                    <tr>
                      <td
                        className="text-center text-color_text-light p-2 uppercase font-semibold"
                        colSpan={2}
                      >
                        Basic info
                      </td>
                    </tr>
                  </thead>
                  <tbody className="">
                    <tr className="border-b">
                      <td className="py-2 px-5">Game time</td>
                      <td>{product.gameTime}min</td>
                    </tr>
                    <tr className="border-b">
                      <td className="py-2 px-5">Number of players</td>
                      <td>{product.numberOfPlayers}</td>
                    </tr>
                    <tr className="border-b">
                      <td className="py-2 px-5">Rating</td>
                      <td className="py-2">
                        <div className="flex items-center gap-1">
                          <span>{product.rating}/5</span>
                          <FaStar className="text-yellow-500 text-xl" />
                        </div>
                      </td>
                    </tr>
                    <tr className="border-b">
                      <td className="py-2 px-5">Complexity</td>
                      <td>{product.complexity}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div className="border max-w-screen-md m-5">
            <h3 className="text-white bg-primary-standard uppercase font-semibold py-2 px-5 text-xl">
              Description
            </h3>
            <p className="p-4">{product.description}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductForm;
