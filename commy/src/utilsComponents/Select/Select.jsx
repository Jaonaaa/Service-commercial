import React, { useState, useEffect, useRef } from "react";
import "./style/style.sass";

const Select = ({ optionsType, name, onChange, fullWidth, title, caretIcon = <CaretDownIcon /> }) => {
  const [selectedOption, setSelectedOption] = useState(
    optionsType.length > 0
      ? {
          value: optionsType[0].value,
          label: optionsType[0].label,
        }
      : { value: "", label: "" }
  );
  const longContent = useRef(null);
  const [openOptions, setOpenOptions] = useState(false);
  const heightNecessary = 16 * 2.6 * optionsType.length;
  const maxHeight = 16 * 2.6 * 5;

  const [widthNecessary, setWidthNecessary] = useState(0);
  const longestText = getMaxLenghtText(optionsType);

  useEffect(() => {
    setSelectedOption(
      optionsType.length > 0
        ? {
            value: optionsType[0].value,
            label: optionsType[0].label,
          }
        : { value: "", label: "" }
    );
    if (optionsType.length > 0) onChange({ target: { name: name, value: optionsType[0].value } });
  }, [optionsType]);

  useEffect(() => {
    let width = longContent.current.getBoundingClientRect().width;
    setWidthNecessary(width + 3.4 * 16);
  }, [optionsType]);

  const handleOpen = () => {
    if (openOptions) setOpenOptions(false);
    else setOpenOptions(true);
  };

  return (
    <>
      <div className={`drop_down_container ${fullWidth ? "fullWidth" : ""}`}>
        {title && <div className="title_select">{title}</div>}
        <div
          className={`drop_down ${fullWidth ? "fullWidth" : ""}`}
          tabIndex={0}
          onBlur={() => {
            setOpenOptions(false);
          }}
        >
          <input type="hidden" value={selectedOption.value} name={name} />
          <div className="container_label" onClick={handleOpen}>
            <div className="text" style={{ width: widthNecessary }}>
              {selectedOption.label}
              <div className="hiddenText" ref={longContent}>
                {longestText}
              </div>
            </div>
            <div
              className="icon"
              style={{
                transform: openOptions ? `rotate(180deg)` : `rotate(0deg)`,
              }}
            >
              {caretIcon}
            </div>
          </div>
          <div
            className={`container_options ${openOptions ? "box_show" : ""}`}
            style={{
              height: openOptions ? heightNecessary : 0,
              maxHeight: maxHeight,
            }}
          >
            {optionsType.map((option, index) => (
              <div
                className="option_drop_down"
                key={index}
                onClick={() => {
                  setSelectedOption(option);
                  if (onChange) onChange({ target: { name: name, value: option.value } });
                }}
              >
                {option.label}
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
};

// use only for { value , label } Array
const getMaxLenghtText = (optionsType) => {
  let textArray = optionsType.filter((option, i, array) => {
    return option.label.length === Math.max(...array.map((option_in) => option_in.label.length));
  });
  if (textArray.length > 0) return textArray[0].label;
  else return "";
};

const CaretDownIcon = () => {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" width="10" height="6.807" viewBox="0 0 12 6.807">
      <path
        id="Icon_awesome-caret-down"
        data-name="Icon awesome-caret-down"
        d="M1.6,13.5H11.987a.806.806,0,0,1,.569,1.376L7.365,20.071a.809.809,0,0,1-1.142,0L1.033,14.876A.806.806,0,0,1,1.6,13.5Z"
        transform="translate(-0.794 -13.5)"
        fill="#707070"
      />
    </svg>
  );
};

export default Select;
