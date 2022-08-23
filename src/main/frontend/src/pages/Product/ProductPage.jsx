import React from "react";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "../../api/axios";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import Product from "./Product";

const ProductForm = () => {
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

  function handleDelete() {
    axiosPrivate
      .delete(`/products/${id}`)
      .then((response) => {
        console.log(response);
        navigate("/boardgames");
      })
      .catch((error) => console.log(error));
  }

  return (
    <div>
      {!loading && <Product product={product} handleDelete={handleDelete} />}
    </div>
  );
};

export default ProductForm;
