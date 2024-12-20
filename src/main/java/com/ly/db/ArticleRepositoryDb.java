package com.ly.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ly.entity.Article;
import com.ly.repository.interfaces.ArticleRepository;

public class ArticleRepositoryDb implements ArticleRepository {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "root";

    @Override
    public void insert(Article data) {
        String sql = "INSERT INTO Article (qte_stock, libelle, montant) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, data.getQteStock());
            statement.setString(2, data.getLibelle());
            statement.setInt(3, data.getMontant());

            statement.executeUpdate();
            System.out.println("Article inséré avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Article> lister() {
        String sql = "SELECT * FROM Article";
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                articles.add(mapArticle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> getArticlesDispo() {
        String sql = "SELECT * FROM Article WHERE qte_stock > 0";
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                articles.add(mapArticle(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public Article getArticlesByLibelle(String libelle) {
        String sql = "SELECT * FROM Article WHERE LOWER(libelle) = LOWER(?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, libelle);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapArticle(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode utilitaire pour mapper un ResultSet à un objet Article
    private Article mapArticle(ResultSet resultSet) throws SQLException {
        return new Article(
                resultSet.getInt("qte_stock"),
                resultSet.getString("libelle"),
                resultSet.getInt("montant")
        );
    }
}
