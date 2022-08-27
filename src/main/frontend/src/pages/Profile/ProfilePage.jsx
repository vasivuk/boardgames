import React, { useEffect, useState } from "react";
import { axiosPrivate } from "../../api/axios";
import FormInput from "../../components/form/FormInput";
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
  const { auth } = useAuth();
  useEffect(() => {
    axiosPrivate
      .get(`/users/user?email=${auth?.email}`)
      .then((response) => setUser(response?.data))
      .catch((error) => console.log(error));
  }, []);
  return (
    <div className="w-full min-h-screen flex justify-around items-start mt-10 gap-5">
      <div className="flex gap-5 border-2 w-3/5 border-primary-standard bg-secondary-standard text-color_text-light rounded-md p-8">
        <div className="w-1/2 border-r-2 pr-10">
          <div className="flex gap-5 items-center">
            <h1 className="font-thin text-2xl tracking-wider mt-3">
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

          <FormInput
            label="First Name"
            name="firstName"
            readOnly
            type="text"
            value={user.firstName}
          />

          <FormInput
            label="Last Name"
            name="lastName"
            readOnly
            type="text"
            value={user.lastName}
          />

          <FormInput
            label="Email"
            name="email"
            readOnly
            type="email"
            value={user.email}
          />

          <FormInput
            label="Country"
            name="country"
            type="text"
            value={user.country}
          />

          <FormInput
            label="City"
            name="city"
            readOnly
            type="text"
            value={user.city}
          />

          <FormInput
            label="Address"
            name="address"
            readOnly
            type="text"
            value={user.address}
          />
        </div>
        <div className="w-1/2 pl-5">
          <h1 className="font-thin text-2xl tracking-wider mt-3">Orders</h1>
          <table className="w-full text-sm text-left text-gray-50 mt-5">
            <thead className="text-xs text-white uppercase bg-primary-standard">
              <tr>
                <th className="py-3 px-6">Order No.</th>
                <th className="py-3 px-6">Total price</th>
              </tr>
            </thead>
            <tbody>
              <tr className="bg-neutral-300 text-color_text-dark border-b py-3 px-6">
                <td className="py-3 px-6">123123</td>
                <td>500.00 EUR</td>
              </tr>
              <tr className="bg-neutral-300 text-color_text-dark border-b py-3 px-6">
                <td className="py-3 px-6">123123</td>
                <td>500.00 EUR</td>
              </tr>
              <tr className="bg-neutral-300 text-color_text-dark border-b">
                <td className="py-3 px-6">123123</td>
                <td>500.00 EUR</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
