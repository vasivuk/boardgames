import React from "react";
import { useState, useEffect } from "react";
import { useParams, useNavigate, useOutletContext } from "react-router-dom";
import axios from "../../api/axios";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import Product from "./Product";

const ProductForm = () => {
  const [cart, setCart] = useOutletContext();
  const [count, setCount] = useState(1);
  const [subTotal, setSubTotal] = useState();

  const axiosPrivate = useAxiosPrivate();
  let { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`products/${id}`);
        setProduct(response.data);
        setLoading(false);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [id]);

  //Change subtotal whenever count updates
  useEffect(() => {
    setSubTotal(product?.price * count);
  }, [product, count]);

  function handleDelete() {
    axiosPrivate
      .delete(`/products/${id}`)
      .then((response) => {
        console.log(response);
        navigate("/boardgames");
      })
      .catch((error) => console.log(error));
  }

  console.log(cart);
  function handleIncrement() {
    setCount((prevCount) => prevCount + 1);
  }

  function handleDecrement() {
    count > 1 && setCount((prevCount) => prevCount - 1);
  }

  function handleAddToCart(e) {
    e.preventDefault();
    const cartItem = {
      product,
      count,
      subTotal,
    };
    //If the product is already in the cart
    if (
      cart.some((productInCart) => productInCart?.product?.id === product.id)
    ) {
      //Increment the count of that product
      setCart((prevCart) =>
        prevCart.map((prevItem) => {
          if (prevItem?.product?.id === product?.id) {
            return {
              ...prevItem,
              count: prevItem?.count + count,
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
  }

  return (
    <div>
      {!loading && <Product product={product} handleDelete={handleDelete} />}
      <div className="p-5 flex justify-center items-center gap-3">
        <button
          className="w-7 h-7 bg-primary-standard rounded-xl text-white"
          onClick={handleDecrement}
        >
          -
        </button>
        <input
          type="text"
          className="border border-gray-400 w-7 h-7 text-center"
          value={count}
          readOnly
        />
        <button
          className="px-2 bg-primary-standard rounded-xl text-white w-7 h-7"
          onClick={handleIncrement}
        >
          +
        </button>
        <button
          onClick={handleAddToCart}
          className="rounded text-color_text-dark font-semibold bg-secondary-standard py-2 px-5 enabled:hover:bg-secondary-standard disabled:opacity-50"
        >
          Add to Cart
        </button>
      </div>
    </div>
  );
};

export default ProductForm;
