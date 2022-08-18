import axios from "../api/axios";

class CategoryService {
  getAllCategories() {
    return axios.get("/categories");
  }
  createCategory(category) {
    return axios.post("/categories/create", category);
  }
  findCategories(searchParam) {
    return axios.get(`/categories/name?search=${searchParam}`);
  }
}

export default new CategoryService();
