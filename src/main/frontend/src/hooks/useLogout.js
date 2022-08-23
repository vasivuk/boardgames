import useAuth from "./useAuth";
import useAxiosPrivate from "./useAxiosPrivate";
const useLogout = () => {
  const { setAuth } = useAuth();
  const axiosPrivate = useAxiosPrivate();

  const logout = async () => {
    setAuth({});
    try {
      const response = await axiosPrivate.get("/logout", {
        withCredentials: true,
      });
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  return logout;
};

export default useLogout;
