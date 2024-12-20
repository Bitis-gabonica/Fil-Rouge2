package com.ly.repository.interfaces;

import java.util.List;

import com.ly.entity.Article;
import com.ly.repository.Repository;

public interface ArticleRepository extends Repository<Article> {

    public List<Article> getArticlesDispo();
    public Article getArticlesByLibelle(String libelle);
}
