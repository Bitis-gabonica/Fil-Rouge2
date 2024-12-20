package com.ly.views.implement;

import java.util.List;
import java.util.Scanner;

import com.ly.entity.Article;
import com.ly.views.Interfaces.ArticleViews;

public class ArticleViewsImpl implements ArticleViews {
    private final Scanner sc;

    public ArticleViewsImpl(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public Article saisie(){

        System.out.println("Entrez qteStock");
        int qteStock=sc.nextInt();
        System.out.println("Entrez libelle");
        String libelle=sc.next();
        System.out.println("Entrez montant");
        int montant=sc.nextInt();
        Article article=new Article(qteStock, libelle, montant);
        return article;
    
    }


    @Override
    public void lister(List<Article> articles){
        for (int i = 0; i <articles.size(); i++) {
            Article article=articles.get(i);
            System.out.println(article);
        }
    }
}
