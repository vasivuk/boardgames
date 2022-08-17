import axios from "../api/axios";

class ProductService {
  getAllProducts() {
    return axios.get("/products");
  }
  getProductById(id) {
    return axios.get(`products/${id}`);
  }
  createProduct(product) {
    return axios.post("/products/create", product);
  }
}

export default new ProductService();
