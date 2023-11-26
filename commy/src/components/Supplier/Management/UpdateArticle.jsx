import React, { useEffect, useState } from "react";
import Input from "../../../utilsComponents/Input/Input";
import Select from "../../../utilsComponents/Select/Select";
import { alaivoGet, alaivoPost, alaivoPut } from "../../../utils/Alaivo";

const UpdateArticle = ({ addNotifs }) => {
  const [formData, setFormData] = useState({
    article: undefined,
    price_HT: undefined,
    supplier: undefined,
    quantity: undefined,
    tva: undefined,
  });
  const { articles, id_supplier, fetchArticle } = useData();
  const handleInput = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };
  const insertArticle = (e) => {
    e.preventDefault();
    let data = {
      ...formData,
      id: formData.article.id,
      article: formData.article.article,
      supplier: { id: id_supplier },
    };

    console.log(data);
    if (formData.tva !== undefined)
      alaivoPut("supplier/article", JSON.stringify(data))
        .then((res) => {
          addNotifs(res.status, res.details);
          console.log(res);
          fetchArticle();
        })
        .catch((err) => {
          addNotifs(err.status, err.details);
          console.log(err);
        });
    else addNotifs("error", "Add article Data not correct");
  };
  return (
    <div className="manage_container">
      <div className="title">Update Article </div>
      <form action="" method="post" onSubmit={insertArticle}>
        <div className="row">
          <Select title={"Article "} fullWidth name={"article"} onChange={handleInput} optionsType={articles} />
        </div>
        <div className="row">
          <Input
            title={"Price HT"}
            type="number"
            name="price_HT"
            onChange={handleInput}
            id={"price_HT"}
            defaultValue={formData.article ? formData.article.price_HT : "0"}
            fullWidth
            required
          />
          <Input
            title={"TVA"}
            type="number"
            name="tva"
            defaultValue={formData.article ? formData.article.tva : "0"}
            onChange={handleInput}
            id={"tva"}
            fullWidth
            required
          />
        </div>
        <div className="row">
          <Input
            title={"Quantity"}
            name="quantity"
            type="number"
            defaultValue={formData.article ? formData.article.quantity : "0"}
            onChange={handleInput}
            id={"quantity"}
            fullWidth
            required
          />
        </div>

        <button className="btn_validate"> Validate </button>
      </form>
    </div>
  );
};

const useData = () => {
  const details = localStorage.getItem("details_user_") ? JSON.parse(localStorage.getItem("details_user_")) : undefined;
  const id_supplier = details.supplier.id;
  const [articles, setArticles] = useState([]);
  useEffect(() => {
    fetchArticle();
  }, []);

  const fetchArticle = () => {
    alaivoGet(`supplier/${id_supplier}/article`)
      .then((res) => {
        console.log(res.data);
        const result = res.data.map((re) => ({ value: re, label: re.article.name }));
        setArticles(result);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return { articles, id_supplier, fetchArticle };
};

export default UpdateArticle;
