import axios from "../api/axios";

class UserService {
  saveUser(user) {
    return axios.post("/register", user);
  }
  login(user) {
    const params = new URLSearchParams();
    params.append("email", user.email);
    params.append("password", user.password);
    return axios.post("/login", params, { withCredentials: true });
  }
  refreshToken() {
    return axios.get("/token/refresh", { withCredentials: true });
  }
}

export default new UserService();
