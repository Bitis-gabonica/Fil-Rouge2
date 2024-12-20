package com.ly.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ly.repository.Repository;

public abstract class RepositoryDb<T> implements Repository<T> {

    protected final String jdbcUrl = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
    protected final String jdbcUser = "postgres";
    protected final String jdbcPassword = "root";

    protected abstract String getInsertQuery(); // Requête SQL pour l'insertion
    protected abstract String getSelectAllQuery(); // Requête SQL pour sélectionner tous les éléments
    protected abstract void setInsertParameters(PreparedStatement statement, T data) throws SQLException; // Définit les paramètres pour l'insertion
    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException; // Mappe un ResultSet à un objet T

    @Override
    public void insert(T data) {
        String sql = getInsertQuery();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setInsertParameters(statement, data); // Définit les paramètres spécifiques pour T
            statement.executeUpdate();
            System.out.println("Données insérées avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> lister() {
        String sql = getSelectAllQuery();
        List<T> items = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                items.add(mapResultSet(resultSet)); // Mappe chaque ligne à un objet T
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
