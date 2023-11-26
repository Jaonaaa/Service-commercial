package com.commercial.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.Models.Article;

public interface ArticleRepo extends JpaRepository<Article, Long> {

}
