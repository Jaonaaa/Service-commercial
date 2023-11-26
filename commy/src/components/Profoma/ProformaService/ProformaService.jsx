import React, { useEffect, useState } from "react";
import "./ProformaService.sass";
import { alaivoGet, URL } from "../../../utils/Alaivo";
import { formatInterfaceToTime, formatTimToInterface } from "../../../utils/Time";
import { handleLogoStaticIn } from "../../Navbar/HeadNav/HeadNav";
import Modal from "../../../utilsComponents/Modal/Modal";
import { AnimatePresence } from "framer-motion";
import PurchaseList from "./PurchaseList";
import CrossIcon from "../../../assets/svg/CrossIcon";
import CheckedIcon from "../../../assets/svg/CheckedIcon";
import DonwloadIcon from "../../../assets/svg/DonwloadIcon";

const ProformaService = ({ addNotifs }) => {
  const { proforma, askPurchaseOrder } = useData(addNotifs);
  return (
    <div className="container_proform_service">
      <div className="title">Proforma</div>
      <div className="list">
        {proforma.map((proforma, index) => (
          <RowProformas key={index} askPurchaseOrder={askPurchaseOrder} addNotifs={addNotifs} proforma={proforma} />
        ))}
      </div>
    </div>
  );
};

const RowProformas = ({ proforma, addNotifs }) => {
  const [openModal, closeModal] = useState(false);
  const [purchaseList, setPurchaseList] = useState([]);
  const validation = proforma.proformas[0].requestProforma.requestLinkProforma.validation;
  const id_link = proforma.proformas[0].requestProforma.requestLinkProforma.id;

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

  return (
    <>
      <div className="row_list">
        <div className="title">
          Date : <span className="date"> {proforma.date} </span>
        </div>
        <div className="tube">
          <div className="slider">
            {proforma.proformas.map((pro) => (
              <BlockProforma data={pro} key={pro.id} id_link={id_link} />
            ))}
          </div>
          <div
            className={`btn ${validation == 1 ? "wait" : validation == 2 ? "done" : ""}`}
            onClick={async () => {
              if (validation != 2) {
                await askPurchaseOrder(id_link);

                handleModal();
              }
            }}
          >
            {validation === 0 ? " Purchase order" : "Waiting validation"}
          </div>
        </div>
        <AnimatePresence>
          {openModal && (
            <Modal closer={handleModal}>
              <PurchaseList
                addNotifs={addNotifs}
                purchaseList={purchaseList}
                id_link={id_link}
                closeModal={handleModal}
                validation={validation}
              />
            </Modal>
          )}
        </AnimatePresence>
      </div>
    </>
  );
};

const BlockProforma = ({ data, id_link }) => {
  return (
    <>
      <div className="block_proforma">
        <div className="top">
          <div className="logo"> {handleLogoStaticIn(data.supplier.logo)}</div>
        </div>
        <div className="bottom">
          <div className="info">
            <div className="name"> {data.supplier.name}</div>
            <div className="localisation">{data.supplier.localisation.name} </div>
          </div>
          <div className="status">
            {data.answered ? (
              <div className="green blo">
                {" "}
                <CheckedIcon />{" "}
              </div>
            ) : (
              <div className="red blo">
                <CrossIcon />
              </div>
            )}
            {data.answered ? (
              <a href={URL + "pdf/proforma/" + id_link + "/" + data.supplier.id}>
                <div className="download blo">
                  <DonwloadIcon />
                </div>
              </a>
            ) : (
              ""
            )}
          </div>
        </div>
      </div>
      {}
    </>
  );
};

const useData = (addNotifs) => {
  const [proforma, setProforma] = useState([]);
  useEffect(() => {
    fetchProforma();
  }, []);

  const fetchProforma = () => {
    alaivoGet("requestsc/proforma").then((res) => {
      let data = res.data;
      let container = [];
      let inner = [];

      console.log(res);
      for (let i = 0; i < data.length; i++) {
        if (i + 1 < data.length) {
          if (data[i + 1].requestProforma.id === data[i].requestProforma.id) {
            inner.push(data[i]);
          } else {
            inner.push(data[i]);
            container.push({ date: formatTimToInterface(data[i].requestProforma.date), proformas: inner });
            inner = [];
          }
        } else {
          inner.push(data[i]);
          container.push({ date: formatTimToInterface(data[i].requestProforma.date), proformas: inner });
        }
      }
      // sort date to DESC
      container.sort(
        (a, b) => new Date(formatInterfaceToTime(b.date)).getTime() - new Date(formatInterfaceToTime(a.date)).getTime()
      );
      setProforma(container);
      console.log(container);
    });
  };

  return { proforma, fetchProforma };
};

export default ProformaService;
