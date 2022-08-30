import React, { useEffect, useState } from "react";
import { axiosPrivate } from "../../api/axios";
import useAuth from "../../hooks/useAuth";
import { FiLogOut } from "react-icons/fi";
import useLogout from "../../hooks/useLogout";

const ProfilePage = () => {
  const logout = useLogout();

  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    country: "",
    city: "",
    address: "",
  });

  const [orders, setOrders] = useState([]);

  const ordersElement = orders.map((order) => (
    <tr
      key={order?.id}
      className="bg-neutral-300 text-color_text-dark border-b py-3 px-6"
    >
      <td className="py-3 px-6">{order?.id}</td>
      <td className="py-3 px-6">{order?.totalPrice}</td>
    </tr>
  ));

  const { auth } = useAuth();

  //Getting user and his orders
  useEffect(() => {
    axiosPrivate
      .get(`/users/user?email=${auth?.email}`)
      .then((response) => {
        setUser(response?.data);
        console.log(response?.data?.id);
        axiosPrivate
          .get(`users/${response?.data?.id}/orders`)
          .then((response) => setOrders(response?.data))
          .catch((error) => console.log(error));
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <div className="w-full min-h-screen flex justify-around items-start mt-10 gap-5">
      <div className="flex gap-5 border-2 w-3/5 border-primary-standard bg-secondary-standard text-neutral-700 rounded-md p-8">
        <div className="w-1/2 border-r-2 pr-10">
          <div className="flex gap-5 items-center">
            <h1 className="font-bold text-2xl tracking-wider mt-3">
              User Account
            </h1>
            <button
              onClick={() => logout()}
              className="px-6 py-2 bg-primary-standard text-color_text-light rounded-md flex gap-2 items-center"
            >
              <FiLogOut />
              <p>Logout</p>
            </button>
          </div>
          <h3 className="py-2 border-b ">
            First Name: <span>{user.firstName}</span>
          </h3>
          <h3 className="py-2 border-b">
            Last Name: <span>{user.lastName}</span>
          </h3>
          <h3 className="py-2 border-b">
            Email: <span>{user.email}</span>
          </h3>
          <h3 className="py-2 border-b">
            Country: <span>{user.country}</span>
          </h3>
          <h3 className="py-2 border-b">
            City: <span>{user.city}</span>
          </h3>
          <h3 className="py-2 border-b">
            Address: <span>{user.address}</span>
          </h3>
        </div>
        <div className="w-1/2 pl-5">
          <h1 className="font-bold text-2xl tracking-wider mt-3">Orders</h1>
          <table className="w-full text-sm text-left text-gray-50 mt-5">
            <thead className="text-xs text-white uppercase bg-primary-standard">
              <tr>
                <th className="py-3 px-6 ">Order No.</th>
                <th className="py-3 px-6 ">Total price</th>
              </tr>
            </thead>
            <tbody>{ordersElement}</tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
