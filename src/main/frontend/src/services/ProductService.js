import axios from "../api/axios";

class ProductService {
  getAllProducts() {
    return axios.get("/products");
  }
  getProductById(id) {
    return axios.get(`products/${id}`);
  }
}

export default new ProductService();
