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
  required,
  readOnly,
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
        {label} {required && <span className="text-red-600">*</span>}
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
        readOnly={readOnly}
      />
    </div>
  );
};

export default FormInput;
