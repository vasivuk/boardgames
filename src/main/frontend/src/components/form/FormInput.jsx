import React from "react";

const FormInput = ({
  type,
  name,
  value,
  onChange,
  placeholder,
  label,
  lblClassName,
  inputClassName,
}) => {
  return (
    <div className="w-full">
      <label
        htmlFor={name}
        className={
          "block text-sm font-normal " +
          (lblClassName ? lblClassName : "text-color_text-light")
        }
      >
        {label}
      </label>
      <input
        type={type}
        id={name}
        name={name}
        placeholder={placeholder}
        className={
          "h-9 w-full my-1 px-2 py-2 text-color_text-dark rounded " +
          (inputClassName && inputClassName)
        }
        onChange={onChange}
        value={value}
      />
    </div>
  );
};

export default FormInput;
