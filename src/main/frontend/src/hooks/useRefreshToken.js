import axios from "../api/axios";
import useAuth from "./useAuth";

const useRefreshToken = () => {
  const { setAuth } = useAuth();

  const refresh = async () => {
    try {
      const response = await axios.get("/token/refresh", {
        withCredentials: true,
      });
      setAuth((prev) => {
        return {
          ...prev,
          role: response.data.authority,
          accessToken: response.data.accessToken,
          email: response.data.email,
        };
      });
      return response.data.accessToken;
      //If refresh token has expired, removes authentication token, and forces user to log in again
    } catch (error) {
      setAuth({});
    }
  };
  return refresh;
};

export default useRefreshToken;
