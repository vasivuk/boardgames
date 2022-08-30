import React, { useState } from "react";
import { FaCheck, FaInfoCircle } from "react-icons/fa";

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
  valid,
  validMsg,
}) => {
  const [focus, setFocus] = useState(false);

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
        {valid && <FaCheck className="inline" />}
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
        onFocus={() => setFocus(true)}
        onBlur={() => setFocus(false)}
      />
      <p
        className={
          focus && value && !valid && validMsg
            ? "flex bg-neutral-800 items-center p-2 gap-2 text-white rounded font-light max-w-fit "
            : "hidden"
        }
      >
        <FaInfoCircle /> {validMsg}
      </p>
    </div>
  );
};

export default FormInput;
