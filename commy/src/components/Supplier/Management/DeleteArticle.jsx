import React, { useEffect, useState } from "react";
import Select from "../../../utilsComponents/Select/Select";
import { alaivoDelete, alaivoGet } from "../../../utils/Alaivo";

const DeleteArticle = ({ addNotifs }) => {
  const [formData, setFormData] = useState({
    article: undefined,
  });
  const { articles, fetchArticle } = useData();
  const handleInput = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const removeArticle = (e) => {
    e.preventDefault();
    if (formData.article !== undefined)
      alaivoDelete("supplier/article/" + formData.article.id)
        .then((res) => {
          addNotifs(res.status, res.details);
          fetchArticle();
          console.log(res);
        })
        .catch((err) => {
          addNotifs(err.status, err.details);
          console.log(err);
        });
    else addNotifs("error", "Remove article Data not correct");
  };

  return (
    <div className="manage_container">
      <div className="title"> Remove Article </div>
      <form action="" method="post" onSubmit={removeArticle}>
        <div className="row">
          <Select title={"Article"} fullWidth name={"article"} onChange={handleInput} optionsType={articles} />
        </div>
        <button className="btn_validate"> Remove </button>
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
  return { articles, fetchArticle };
};

export default DeleteArticle;
