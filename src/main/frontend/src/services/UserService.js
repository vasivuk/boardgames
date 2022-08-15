import axios from "axios";

const BOARDGAMES_API_BASE_URL = "http://127.0.0.1:8080/api";

class UserService {
  saveUser(user) {
    return axios.post(BOARDGAMES_API_BASE_URL + "/register", user);
  }
  login(user) {
    const params = new URLSearchParams();
    params.append("email", user.email);
    params.append("password", user.password);
    return axios.post(BOARDGAMES_API_BASE_URL + "/login", params);
  }
}

export default new UserService();
