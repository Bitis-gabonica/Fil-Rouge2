package com.ly.services.interfaces;

import java.util.List;

import com.ly.entity.Article;

public interface ArticleService {


     public void add(Article article);
     public List<Article> list();
     public List<Article> listArticlesDispo();
     public  void updateQteStock(int montant, Article article);
     public Article getArticleByLibelle(String libelle);
}
