import React, { useEffect, useState } from "react";
import { alaivoGet, alaivoPost } from "../../../../utils/Alaivo";

import DetailsMessage from "./DetailsMessage/DetailsMessage";
import { handleLogoStaticIn } from "../../../Navbar/HeadNav/HeadNav";
import { formatTimToInterface } from "../../../../utils/Time";

import "./MessageBox.sass";
import SearchIcon from "../../../../assets/svg/SearchIcon";
import CheckedIcon from "../../../../assets/svg/CheckedIcon";

const MessageBox = ({ addNotifs }) => {
  const { validate, invalidate, fetchDetails, requestDetails, setRequestDetails, validateRequest } = useData(addNotifs);
  const [validate_s, setStatus] = useState(false); // kamo be za XD
  const [requestAccepted, setRequestAccepted] = useState([]);

  const handleValidate = () => {
    setRequestDetails([]);
    setStatus(!validate_s);
  };
  const addRequest = (id) => {
    setRequestAccepted((prev) => [...prev, id]);
  };
  const removeRequest = (id) => {
    const filtred = requestAccepted.filter((request_id) => request_id !== id);
    setRequestAccepted(filtred);
  };

  return (
    <div className="container_message_box">
      <div className="container_message">
        <div className="title">
          Request notifs
          {requestAccepted.length > 0 && (
            <div
              className="btn_proforma"
              onClick={() => {
                validateRequest(requestAccepted);
              }}
            >
              Validate
            </div>
          )}
        </div>

        <div className="slider_message">
          <div className={`block ${!validate_s ? "active" : ""}`} onClick={handleValidate}>
            Invalidate
          </div>
          <div className={`block ${validate_s ? "active" : ""}`} onClick={handleValidate}>
            Validate
          </div>
        </div>

        <div className="list">
          {validate_s &&
            validate.map((va) => {
              const logo = handleLogoStaticIn(va.serviceSender.logo);
              const name = va.serviceSender.name;
              const datetime = formatTimToInterface(va.date);

              return (
                <div
                  className="row"
                  key={va.id}
                  onClick={() => {
                    fetchDetails(va.id);
                  }}
                >
                  <div className="upper">
                    <div className="service_logo"> {logo}</div>
                    <div className="service_name"> {name}</div>
                    <div className="date">{datetime}</div>
                  </div>
                  <div className="bottom">
                    <div
                      className="see"
                      onClick={() => {
                        fetchDetails(va.id);
                      }}
                    >
                      <SearchIcon />
                    </div>
                  </div>
                </div>
              );
            })}

          {!validate_s &&
            invalidate.map((va) => {
              const logo = handleLogoStaticIn(va.serviceSender.logo);
              const name = va.serviceSender.name;
              const datetime = formatTimToInterface(va.date);
              const included = requestAccepted.includes(va.id);
              return (
                <div className="row" key={va.id}>
                  <div className="upper">
                    <div className="service_logo"> {logo}</div>
                    <div className="service_name"> {name}</div>
                    <div className="date">{datetime}</div>
                  </div>
                  <div className="bottom">
                    <div
                      className="see"
                      onClick={() => {
                        fetchDetails(va.id);
                      }}
                    >
                      <SearchIcon />
                    </div>
                    <div
                      className={`accept ${included ? "included" : ""}`}
                      onClick={() => {
                        if (!included) addRequest(va.id);
                        else removeRequest(va.id);
                      }}
                    >
                      <CheckedIcon />
                    </div>
                  </div>
                </div>
              );
            })}
        </div>
      </div>
      <DetailsMessage details={requestDetails} />
    </div>
  );
};

const useData = (addNotifs) => {
  const [validate, setValidate] = useState([]);
  const [invalidate, setInValidate] = useState([]);
  const [requestDetails, setRequestDetails] = useState([]);

  useEffect(() => {
    fetchRequest();
  }, []);

  const fetchRequest = () => {
    alaivoGet("requestsc").then((res) => {
      setInValidate(res.data.invalidates);
      setValidate(res.data.validates);
    });
  };

  const validateRequest = (list) => {
    alaivoPost("requestsc/v", JSON.stringify(list)).then((res) => {
      addNotifs(res.status, res.details);
      fetchRequest();
    });
  };

  const fetchDetails = (id) => {
    alaivoGet("requestsc/" + id).then((res) => {
      const details = res.data.map((data) => ({
        article: data.article.name,
        categorie: data.article.categorie.name,
        quantity: +data.quantity,
      }));
      setRequestDetails(details);
    });
  };

  return { validate, invalidate, requestDetails, fetchDetails, setRequestDetails, validateRequest };
};

export default MessageBox;
