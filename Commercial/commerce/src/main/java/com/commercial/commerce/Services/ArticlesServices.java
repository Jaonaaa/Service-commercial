package com.commercial.commerce.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.commercial.commerce.Models.Article;
import com.commercial.commerce.Repository.ArticleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticlesServices {

    private final ArticleRepo articleRepo;

    public List<Article> getAllArticles() {

        return articleRepo.findAll();
    }

}
