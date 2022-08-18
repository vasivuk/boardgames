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
  updateProduct(product) {
    return axios.put(`/products/${product.id}`, product);
  }
  deleteProduct(id) {
    return axios.delete(`/products/${id}`);
  }
}

export default new ProductService();
