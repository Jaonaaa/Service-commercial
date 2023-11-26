import React, { useEffect, useState } from "react";
import "./RequestSCForm.sass";
import { alaivoGet } from "../../../utils/Alaivo";
import Select from "../../../utilsComponents/Select/Select";
import Table from "../../../utilsComponents/Table/Table";
import Input from "../../../utilsComponents/Input/Input";
import { AnimatePresence } from "framer-motion";
import Modal from "../../../utilsComponents/Modal/Modal";
import FormSC from "./FormSC/FormSC";

const dataTable = {
  titles: ["Article", "Categorie", ""],
  index: ["article", "categorie", "func"],
};

const RequestSCForm = ({ addNotifs }) => {
  const { articles, setArticles, articlesTotal, articlesSelected } = useData();
  const [articlesInList, setArticleInList] = useState([]);
  const [openModal, closeModal] = useState(false);
  const filterArticle = (e) => {
    const searchedText = e.target.value.toLowerCase().trim();
    const articlesFiltred = articlesTotal.filter(
      (article) => article.article.toLowerCase().indexOf(searchedText) != -1
    );
    if (searchedText !== "") setArticles(articlesFiltred);
    else setArticles(articlesTotal);
  };

  useEffect(() => {
    if (articlesSelected.length > 0) addArticle(articlesSelected[0]);
  }, [articlesSelected]);

  const addArticle = (article) => {
    console.log(article);
    const filtredArticled = articlesInList.filter((article_) => article_.id !== article.id);
    setArticleInList([...filtredArticled, article]);
  };
  const removeArticle = (id) => {
    const filtredArticled = articlesInList.filter((article_) => article_.id !== id);
    setArticleInList([...filtredArticled]);
  };
  const handleModal = () => {
    closeModal(!openModal);
  };

  return (
    <div className="container_request_form">
      <div className="title">Send Request to Commercial </div>
      <div className="subtitle">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Ipsa quia natus iste animi.
      </div>

      <div className="content">
        <div className="form">
          <Input onChange={filterArticle} title={"Filter article"} />
          <button onClick={handleModal}>Validate</button>
        </div>
        <div className="table_container_request">
          <Table {...dataTable} body={articles} />
          <div className="list_request">
            <div className="title">Articles selected </div>
            {articlesInList.map((article) => (
              <div className="row_list" key={article.id}>
                <div className="name">{article.article}</div>
                <div className="categorie">{article.categorie}</div>
                <div
                  className="func"
                  onClick={() => {
                    removeArticle(article.id);
                  }}
                >
                  -
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
      <AnimatePresence>
        {openModal && (
          <Modal closer={handleModal}>
            <FormSC listArticle={articlesInList} addNotifs={addNotifs} />
          </Modal>
        )}
      </AnimatePresence>
    </div>
  );
};

const useData = () => {
  const [articles, setArticles] = useState([]);
  const [articlesTotal, setArticlesTotal] = useState([]);
  const [articlesSelected, setArticleSelected] = useState([]);

  useEffect(() => {
    fetchArticles();
  }, []);

  const fetchArticles = () => {
    alaivoGet("article").then((articles) => {
      console.log(articles);
      const new_articles = articles.data.map((article) => ({
        article: article.name,
        categorie: article.categorie.name,
        func: (
          <div
            className="func"
            onClick={() => {
              addArticle(article);
            }}
          >
            +
          </div>
        ),
      }));
      setArticles(new_articles);
      setArticlesTotal(new_articles);
    });
  };

  const addArticle = (article) => {
    const new_articles = {
      id: article.id,
      article: article.name,
      article_body: article,
      categorie: article.categorie.name,
    };
    //nandra be le add fa bon
    setArticleSelected([new_articles]);
  };

  return { articles, setArticles, articlesTotal, articlesSelected, setArticleSelected };
};

export default RequestSCForm;
