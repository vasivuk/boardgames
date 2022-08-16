import axios from "../api/axios";

class CategoryService {
  getAllCategories() {
    return axios.get("/categories");
  }
  createCategory(category) {
    return axios.post("/categories/create", category);
  }
}

export default new CategoryService();
