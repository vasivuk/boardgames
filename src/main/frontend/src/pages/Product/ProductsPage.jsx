import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import CategoryService from "../../services/CategoryService";
import ProductService from "../../services/ProductService";
import FilterSection from "./FilterSection";
import ProductListSection from "./ProductListSection";

const ProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);

  const [signal, setSignal] = useState(false);

  const [price, setPrice] = useState([0, 200]);
  const [gameTime, setGameTime] = useState([0, 300]);

  const [categories, setCategories] = useState([]);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [searchParam, setSearchParam] = useState("");

  useEffect(() => {
    CategoryService.findCategories(searchParam)
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
        const response = await ProductService.fetchProducts(
          price[0],
          price[1],
          gameTime[0],
          gameTime[1]
        );
        setProducts(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, [signal]);

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-center text-xl py-5">Boardgames</h1>
      <Link to={"./create"}>
        <button className="px-6 py-2 bg-primary-standard text-secondary-standard rounded-md">
          Add New Product
        </button>
      </Link>

      <div className="grid grid-cols-5 gap-4 w-screen">
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
