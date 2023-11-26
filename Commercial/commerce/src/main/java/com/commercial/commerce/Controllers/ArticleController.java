package com.commercial.commerce.Controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.Models.Article;
import com.commercial.commerce.Services.ArticlesServices;
import com.commercial.commerce.Utils.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticlesServices articleServices;

    @GetMapping
    public Status getListArticle() {
        List<Article> articles = articleServices.getAllArticles();
        return Status.builder().status("good").data(articles).build();
    }
}
