package com.ly.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.repository.interfaces.ClientRepository;

public class ClientRepositoryDb implements ClientRepository {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "root";

    @Override
    public void insert(Client data) {
        String sql = "INSERT INTO Client (surname, tel, adresse, montant_dus, user_id) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setString(1, data.getSurname());
            statement.setString(2, data.getTel());
            statement.setString(3, data.getAdresse());
            statement.setFloat(4, data.getMontantDus());
    
            // Vérifiez si un utilisateur est associé
            if (data.getUser() != null) {
                // Vous devez insérer l'utilisateur dans la table Users avant d'utiliser son id
                User user = data.getUser();
                int userId = insertUserIfNotExists(user, connection);
                statement.setInt(5, userId); // Utilise l'id de l'utilisateur inséré
            } else {
                statement.setNull(5, Types.INTEGER); // Pas d'utilisateur associé
            }
    
            statement.executeUpdate();
            System.out.println("Client inséré avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour insérer un utilisateur s'il n'existe pas déjà et retourner son id
    private int insertUserIfNotExists(User user, Connection connection) throws SQLException {
        // Vérifiez si l'utilisateur existe déjà
        String checkSql = "SELECT id FROM Users WHERE login = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setString(1, user.getLogin());
    
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Retourne l'id existant
                }
            }
        }
    
        // Si l'utilisateur n'existe pas, insérez-le
        String insertSql = "INSERT INTO Users (login, password, role, status) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, user.getLogin());
            insertStatement.setString(2, user.getPassword());
            insertStatement.setString(3, user.getRole().name());
            insertStatement.setBoolean(4, user.isStatus());
    
            try (ResultSet resultSet = insertStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Retourne l'id généré
                }
            }
        }
    
        throw new SQLException("Échec de l'insertion ou de la récupération de l'utilisateur.");
    }
    

    @Override
    public List<Client> lister() {
        String sql = "SELECT * FROM Client";
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clients.add(mapClient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public List<Client> listClientsUser() {
        String sql = "SELECT * FROM Client WHERE user_id IS NOT NULL";
        return getClientsByQuery(sql);
    }

    @Override
    public List<Client> listClientsNotUser() {
        String sql = "SELECT * FROM Client WHERE user_id IS NULL";
        return getClientsByQuery(sql);
    }

    @Override
    public Client getClientByPhone(String tel) {
        String sql = "SELECT * FROM Client WHERE tel = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tel);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper Methods

    private List<Client> getClientsByQuery(String sql) {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clients.add(mapClient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private Client mapClient(ResultSet resultSet) throws SQLException {
        Client client = new Client(
                resultSet.getString("surname"),
                resultSet.getString("tel"),
                resultSet.getString("adresse")
        );
        client.setMontantDus(resultSet.getFloat("montant_dus"));

        int userId = resultSet.getInt("user_id");
        if (!resultSet.wasNull()) {
            User user = getUserById(userId); // Fetch the User object from the database
            client.setUser(user);
        }
        return client;
    }

    private User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            null, // Set the Client object if needed
                            Role.valueOf(resultSet.getString("role").toUpperCase()),
                            resultSet.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getUserId(User user) {
        String sql = "SELECT id FROM Users WHERE login = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getLogin());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the user is not found
    }
}
