import React, { useEffect, useState } from "react";
import "./ProformaSupplier.sass";
import { alaivoGet, alaivoPost } from "../../../utils/Alaivo";
import { formatTimToInterface } from "../../../utils/Time";
import { handleLogoStaticIn } from "../../Navbar/HeadNav/HeadNav";
import CrossIcon from "../../../assets/svg/CrossIcon";
import CheckedIcon from "../../../assets/svg/CheckedIcon";

const ProformaSupplier = ({ addNotifs }) => {
  const { proforma, validateProforma } = useData(addNotifs);
  return (
    <div className="container_proform_supplier">
      <div className="title">Proforma</div>
      <div className="list">
        {proforma.map((proforma, index) => (
          <div className="row_list" key={index}>
            <BlockProforma data={proforma} validateProforma={validateProforma} key={proforma.id} />
          </div>
        ))}
      </div>
    </div>
  );
};

const BlockProforma = ({ data, validateProforma }) => {
  return (
    <>
      <div className="block_proforma">
        <div className="top">
          <div className="logo"> {handleLogoStaticIn(data.service.logo)}</div>
          <div className="date">{formatTimToInterface(data.requestProforma.date)}</div>
        </div>
        <div className="bottom">
          <div className="info">
            <div className="name"> {data.service.name}</div>
            <div className="localisation">{data.service.localisation.name} </div>
          </div>
          <div className="status">
            {!data.answered && (
              <div
                className="validate"
                onClick={() => {
                  validateProforma(data.id);
                }}
              >
                <CheckedIcon />
              </div>
            )}
            {data.answered ? (
              <div className="green blo">
                <CheckedIcon />
              </div>
            ) : (
              <div className="red blo">
                <CrossIcon />
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

const useData = (addNotifs) => {
  const details = localStorage.getItem("details_user_") ? JSON.parse(localStorage.getItem("details_user_")) : undefined;
  const [proforma, setProforma] = useState([]);

  useEffect(() => {
    fetchProforma();
  }, []);

  const fetchProforma = () => {
    alaivoGet(`requestsc/supp/${details.supplier.id}/proforma`).then((res) => {
      let data = res.data;
      console.log(data);
      setProforma(data);
    });
  };

  const validateProforma = (id) => {
    alaivoPost(`requestsc/proforma/${id}`).then((res) => {
      addNotifs(res.status, res.details);
      console.log(res);
      fetchProforma();
    });
  };

  return { proforma, validateProforma };
};

export default ProformaSupplier;
