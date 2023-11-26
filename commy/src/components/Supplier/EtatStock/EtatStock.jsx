import React, { useEffect, useState } from "react";

import "./EtatStock.sass";
import { alaivoGet } from "../../../utils/Alaivo";
import Table from "../../../utilsComponents/Table/Table";

const data = {
  headerOn: { title: "Stock state", side_component: "" },
  titles: ["Article", "Price HT", "TVA", "Price TTC", "Quantity"],
  index: ["name", "price_ht", "tva", "price_ttc", "quantity"],
};

const EtatStock = ({ addNotifs }) => {
  const { stock } = useData(addNotifs);
  return (
    <div className="container_stock">
      <Table {...data} body={stock} />
    </div>
  );
};

const useData = (addNotifs) => {
  const details = localStorage.getItem("details_user_") ? JSON.parse(localStorage.getItem("details_user_")) : undefined;
  const [stock, setStock] = useState([]);

  useEffect(() => {
    fetchstock();
  }, []);

  const fetchstock = () => {
    alaivoGet(`supplier/${details.supplier.id}/article`).then((res) => {
      console.log(res);
      const stocksRes = res.data.map((r) => ({
        name: r.article.name,
        price_ht: r.price_HT + " Ar",
        tva: r.tva + "%",
        price_ttc: getPercent(r.tva, r.price_HT) + r.price_HT + " Ar",
        quantity: r.quantity,
      }));
      setStock(stocksRes);
    });
  };

  const getPercent = (percent, value) => {
    return (value * percent) / 100;
  };
  return { stock };
};

export default EtatStock;
