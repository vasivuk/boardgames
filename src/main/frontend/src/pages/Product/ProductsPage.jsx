import React from "react";
import { useState, useEffect } from "react";
import { Link, useSearchParams } from "react-router-dom";
import axios from "../../api/axios";
import FilterSection from "./FilterSection";
import ProductListSection from "./ProductListSection";
import useAuth from "../../hooks/useAuth";

const ProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);

  const { auth } = useAuth();

  let [searchParams, setSearchParams] = useSearchParams();
  const name = searchParams.get("name");

  const [signal, setSignal] = useState(false);

  const [price, setPrice] = useState([0, 200]);
  const [gameTime, setGameTime] = useState([0, 300]);

  const [categories, setCategories] = useState([]);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [searchParam, setSearchParam] = useState("");

  useEffect(() => {
    axios
      .get(`/categories/name?search=${searchParam}`)
      .then((response) => setCategories(response.data))
      .catch(() => setCategories([]));
  }, [searchParam]);

  const addCategory = function (category) {
    if (selectedCategories.includes(category)) {
      console.log("Category already added");
    } else {
      setSelectedCategories((prevSelectedCategories) => [
        ...prevSelectedCategories,
        category,
      ]);
    }
  };

  const removeCategory = function (category) {
    if (selectedCategories.includes(category)) {
      setSelectedCategories(
        selectedCategories.filter((c) => c.id !== category.id)
      );
    } else {
      console.log("Category is not in the list");
    }
  };

  function handlePriceChange(e, newPrice) {
    setPrice(newPrice);
  }

  function handleGameTimeChange(e, newGameTime) {
    setGameTime(newGameTime);
  }
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        let response;
        name
          ? (response = await axios.get("/products", {
              params: {
                name: name,
              },
            }))
          : (response = await axios.get("/products", {
              params: {
                pmin: price[0],
                pmax: price[1],
                tmin: gameTime[0],
                tmax: gameTime[1],
              },
            }));
        setProducts(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, [signal, name]);

  return (
    <div className="flex flex-col items-center min-h-screen p-10">
      <h1 className="text-3xl text-neutral-600 font-bold uppercase p-4">
        Boardgames
      </h1>
      {auth?.role === "ADMIN" && (
        <Link to={"./create"}>
          <button className="px-6 py-2 bg-primary-standard text-color_text-light rounded-md">
            Add New Product
          </button>
        </Link>
      )}
      <div className="grid grid-cols-5 gap-4 overflow-hidden">
        <FilterSection
          price={price}
          categories={categories}
          selectedCategories={selectedCategories}
          gameTime={gameTime}
          addCategory={addCategory}
          removeCategory={removeCategory}
          handlePriceChange={handlePriceChange}
          handleGameTimeChange={handleGameTimeChange}
          searchParam={searchParam}
          setSearchParam={setSearchParam}
          sendSignal={() =>
            setSignal((prevSignal) => (prevSignal = !prevSignal))
          }
        />
        <ProductListSection products={products} loading={loading} />
      </div>
    </div>
  );
};

export default ProductsPage;
