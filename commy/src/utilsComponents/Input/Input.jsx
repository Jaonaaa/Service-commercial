import React, { useEffect, useState } from "react";
import "./Input.sass";

function Input({
  type = "text",
  placeholder = "",
  defaultValue = "",
  title,
  name = "",
  onChange,
  disabled,
  pattern,
  required = false,
  fullWidth = false,
}) {
  const [value, setValue] = useState(defaultValue);
  const [fileLoaded, setFileLoaded] = useState(false);
  const [filePreview, setFilePreview] = useState("");
  const handleValue = (e) => {
    setValue(e.target.value);
    onChange(e);
  };
  const handleValueFile = (e) => {
    setFileLoaded(true);
    onChange(e);
    if (e.target.files.length > 0) {
      let pathFileLoaded = URL.createObjectURL(e.target.files[0]);
      setFilePreview(pathFileLoaded);
    }
  };
  useEffect(() => {
    setValue(defaultValue);
    onChange({ target: { value: defaultValue, name: name } });
  }, [defaultValue]);

  const handleNumeric = (e) => {
    if (isNaN(+e.target.value)) return "";
    setValue(e.target.value);
    onChange(e);
  };

  return (
    <>
      {type !== "file" ? (
        <div className={`input_ ${fullWidth ? "fullwidth" : ""}`}>
          <label htmlFor={name}>{title}</label>
          <input
            autoComplete="true"
            type={type}
            name={name}
            required={required}
            id={name}
            pattern={pattern}
            onChange={type === "numeric" ? handleNumeric : handleValue}
            placeholder={placeholder}
            value={value}
            disabled={disabled}
          />
        </div>
      ) : (
        <div className={`input_ ${fullWidth ? "fullwidth" : ""}`}>
          <div className="label_">{title} </div>
          <label htmlFor={name} className="label_file_container">
            {!fileLoaded ? (
              <>
                <div className="span">Choose a file...</div>{" "}
              </>
            ) : (
              <img src={filePreview} alt="" />
            )}
          </label>
          <input
            style={{ display: "none" }}
            autoComplete="true"
            type={type}
            name={name}
            id={name}
            onChange={handleValueFile}
            placeholder={placeholder}
            files={null}
            disabled={disabled}
          />
        </div>
      )}
    </>
  );
}

export default Input;
