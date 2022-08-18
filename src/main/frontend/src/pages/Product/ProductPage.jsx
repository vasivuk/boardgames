import React from "react";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import ProductService from "../../services/ProductService";
import Product from "./Product";

const ProductForm = () => {
  let { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await ProductService.getProductById(id);
        setProduct(response.data);
        setLoading(false);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [id]);

  function handleDelete() {
    ProductService.deleteProduct(id)
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
