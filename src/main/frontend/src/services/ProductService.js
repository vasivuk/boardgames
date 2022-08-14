import axios from "axios";

const BOARDGAMES_API_BASE_URL = "http://127.0.0.1:8080/api/products";

class ProductService {
  getAllProducts() {
    return axios.get(BOARDGAMES_API_BASE_URL);
  }
  getProductById(id) {
    return axios.get(BOARDGAMES_API_BASE_URL + `/${id}`);
  }
}

export default new ProductService();
