package com.ly.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.repository.interfaces.DetteRepository;

public class DetteReppositoryDb implements DetteRepository {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "root";

   
    @Override
public void insert(Dette data) {
    String sql = "INSERT INTO Dette (date, montant, montant_verser, montant_restant, client_id, status) " +
                 "VALUES (?, ?, ?, ?, (SELECT id FROM Client WHERE tel = ?), ?) RETURNING id";

    try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
         PreparedStatement statement = connection.prepareStatement(sql)) {

        if (data.getClient() == null || data.getClient().getTel() == null) {
            throw new IllegalArgumentException("Le client doit avoir un numéro de téléphone valide !");
        }

        // Conversion de la chaîne de caractères en java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(data.getDate());

        statement.setDate(1, sqlDate); // Utilise l'objet java.sql.Date
        statement.setFloat(2, data.getMontant());
        statement.setFloat(3, data.getMontantVerser());
        statement.setFloat(4, data.getMontantRestant());
        statement.setString(5, data.getClient().getTel());
        statement.setBoolean(6, data.isStatus());

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                data.setId(resultSet.getInt("id")); // Associe l'id généré à l'objet Dette
                System.out.println("Dette insérée avec ID : " + data.getId());
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    @Override
    public List<Dette> lister() {
        String sql = "SELECT * FROM Dette";
        List<Dette> dettes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                dettes.add(mapDette(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dettes;
    }

    @Override
    public Dette findDette(int id) {
        String sql = "SELECT * FROM Dette WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapDette(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dette> listDetteNonSolde() {
        String sql = "SELECT * FROM Dette WHERE montant_restant > 0";
        List<Dette> dettes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                dettes.add(mapDette(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dettes;
    }

    @Override
    public List<Dette> listDetteNonSolde(Client client) {
        String sql = "SELECT * FROM Dette WHERE client_id = (SELECT id FROM Client WHERE tel = ?) AND montant_restant > 0";
        List<Dette> dettes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (client == null || client.getTel() == null) {
                throw new IllegalArgumentException("Le client doit avoir un numéro de téléphone valide !");
            }

            statement.setString(1, client.getTel());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dettes.add(mapDette(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dettes;
    }

    @Override
    public List<Dette> listDettesSoldes() {
        String sql = "SELECT * FROM Dette WHERE montant_restant = 0";
        List<Dette> dettes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                dettes.add(mapDette(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dettes;
    }

    // Méthode utilitaire pour mapper un ResultSet à un objet Dette
    private Dette mapDette(ResultSet resultSet) throws SQLException {
        Client client = getClientByTel(resultSet.getInt("client_id"));

        return new Dette(
                resultSet.getInt("id"),
                resultSet.getString("date"),
                resultSet.getFloat("montant_verser"),
                client
        );
    }

    // Méthode pour récupérer un client par son numéro de téléphone
    private Client getClientByTel(int clientId) {
        String sql = "SELECT * FROM Client WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getString("surname"),
                            resultSet.getString("tel"),
                            resultSet.getString("adresse")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
