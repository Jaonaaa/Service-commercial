import React, { useEffect, useState } from "react";
import Box from "../../../utilsComponents/Box/Box";

import "./PurchaseList.sass";
import { alaivoPost } from "../../../utils/Alaivo";

const PurchaseList = ({ addNotifs, purchaseList = [], closeModal, validation, id_link }) => {
  const [total, setTotal] = useState(0);
  const askValidation = (id_link) => {
    alaivoPost(`purchase/${id_link}/validation`)
      .then((res) => {
        addNotifs(res.status, res.details);
        closeModal();
      })
      .catch((err) => {
        addNotifs("error", err);
      });
  };
  useEffect(() => {
    let total = 0;
    if (purchaseList !== null)
      purchaseList.map((purchase) => {
        total += getTotal(purchase);
      });
    setTotal(total);
  }, []);
  return (
    <Box>
      <div className="container_purchase_list">
        <div className="title">Purchase Order</div>
        <div className="total">
          Total : <span> {total} Ar</span>
        </div>
        <div className="tab_purchase">
          {purchaseList !== null
            ? purchaseList.map((purchase, index) => {
                const total = getTotal(purchase);
                const quantityTotal = purchase.map((pur) => pur.quantity).reduce((acc, curr) => acc + curr);
                return (
                  <div className="list" key={index}>
                    <div className="head">
                      <div className="article">
                        Article : <span className="article_name">{purchase[0].articleSupplier.article.name}</span>{" "}
                      </div>
                      <div className="quantity"> Qté : {quantityTotal} </div>
                      <div className="total">Total : {total} Ar (TTC)</div>
                    </div>
                    {purchase.map((row, index) => (
                      <div className="row_supplier" key={index}>
                        <div className="name">
                          {" "}
                          {row.articleSupplier.supplier.name}
                          <span>{row.articleSupplier.supplier.localisation.name}</span>
                        </div>
                        <div className="quantity">Qté : {row.quantity}</div>{" "}
                        <div className="ht"> {row.articleSupplier.price_HT} Ar (HT)</div>
                        <div className="tva"> TVA {row.articleSupplier.tva}%</div>
                        <div className="price">
                          {" "}
                          {getPercent(row.articleSupplier.tva, row.articleSupplier.price_HT) +
                            row.articleSupplier.price_HT}{" "}
                          Ar (TTC)
                        </div>
                      </div>
                    ))}
                  </div>
                );
              })
            : ""}
        </div>
        <div className={`btn ${validation === 1 ? "waiting" : ""}`}>
          <button
            onClick={() => {
              if (validation === 0) askValidation(id_link);
            }}
          >
            {validation === 0 ? "Ask Validation" : "Waiting for validation"}
          </button>
        </div>
      </div>
    </Box>
  );
};

const getTotal = (data) => {
  let total = 0;
  data.forEach((row) => {
    total += getTotalIn(row);
  });
  return total;
};
const getTotalIn = (data) => {
  let quantity = data.quantity;
  return (
    quantity * (data.articleSupplier.price_HT + getPercent(data.articleSupplier.tva, data.articleSupplier.price_HT))
  );
};

const getPercent = (percent, value) => {
  return (value * percent) / 100;
};

export default PurchaseList;
