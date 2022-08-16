import axios from "../api/axios";

class UserService {
  saveUser(user) {
    return axios.post("/register", user);
  }
  login(user) {
    const params = new URLSearchParams();
    params.append("email", user.email);
    params.append("password", user.password);
    return axios.post("/login", params);
  }
}

export default new UserService();
