import React, { useEffect, useState } from "react";
import { alaivoGet, alaivoPost } from "../../../utils/Alaivo";
import PurchaseList from "../../Profoma/ProformaService/PurchaseList";
import Modal from "../../../utilsComponents/Modal/Modal";
import { AnimatePresence } from "framer-motion";
import { formatTimToInterface } from "../../../utils/Time";

import "./AdminValidation.sass";
import FinanceLogo from "../../../assets/Logo/FinanceLogo";

const AdminValidation = ({ addNotifs }) => {
  const { request } = useData();
  return (
    <div className="container_admin_validation">
      <div className="title">Request Validation</div>
      <div className="list">
        {request.map((req, index) => (
          <RowRequest request={req} key={index} addNotifs={addNotifs} />
        ))}
      </div>
    </div>
  );
};

const RowRequest = ({ addNotifs, request }) => {
  const user_details = localStorage.getItem("details_user_")
    ? JSON.parse(localStorage.getItem("details_user_"))
    : undefined;
  const service = user_details.service;
  const validation = request.requestLinkProforma.validation;
  const id = request.requestLinkProforma.id;
  const [openModal, closeModal] = useState(false);
  const [purchaseList, setPurchaseList] = useState([]);

  const handleModal = () => {
    closeModal(!openModal);
  };

  const askPurchaseOrder = async (id) => {
    return new Promise((resolve, reject) => {
      alaivoGet("purchase/" + id).then((res) => {
        addNotifs(res.status, res.details);
        setPurchaseList(res.data);
        resolve(res);
        console.log(res);
      });
    });
  };
  const validatePurchaseOrder = (id) => {
    return new Promise((resolve, reject) => {
      alaivoPost("purchase/" + id + "/validation/f").then((res) => {
        addNotifs(res.status, res.details);
        setPurchaseList(res.data);
        console.log(res);
      });
    });
  };

  return (
    <>
      <div className="row">
        <div className="up">
          <div className="logo">
            <FinanceLogo />
          </div>
          <div className="date">Date : {formatTimToInterface(request.date)}</div>
        </div>
        <div className="bottom">
          <button
            onClick={async () => {
              await askPurchaseOrder(id);
              handleModal();
            }}
          >
            {" "}
            Look{" "}
          </button>
          {service.logo === "financeLogo" && (
            <button
              onClick={() => {
                validatePurchaseOrder(id);
              }}
            >
              Validate
            </button>
          )}
        </div>
      </div>
      <AnimatePresence>
        {openModal && (
          <Modal closer={handleModal}>
            <PurchaseList
              addNotifs={addNotifs}
              purchaseList={purchaseList}
              id_link={id}
              closeModal={handleModal}
              validation={validation}
            />
          </Modal>
        )}
      </AnimatePresence>
    </>
  );
};

const useData = () => {
  const [request, setRequest] = useState([]);

  useEffect(() => {
    fetchRequest();
  }, []);

  const fetchRequest = () => {
    alaivoGet("purchase/n_validation").then((request) => {
      console.log(request.data);
      setRequest(request.data);
    });
  };

  return { request };
};

export default AdminValidation;
