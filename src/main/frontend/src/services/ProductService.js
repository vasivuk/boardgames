import axios from "../api/axios";

class ProductService {
  fetchProducts(pmin, pmax, tmin, tmax) {
    return axios.get("/products", {
      params: {
        pmin: pmin,
        pmax: pmax,
        tmin: tmin,
        tmax: tmax,
      },
    });
  }
  getProductById(id) {
    return axios.get(`products/${id}`);
  }
  createProduct(product) {
    return axios.post("/products/create", product);
  }
  updateProduct(product) {
    return axios.put(`/products/${product.id}`, product);
  }
  deleteProduct(id) {
    return axios.delete(`/products/${id}`);
  }
  fetchProductsByName(name) {
    return axios.get("/products", {
      params: {
        name: name,
      },
    });
  }
}

export default new ProductService();
