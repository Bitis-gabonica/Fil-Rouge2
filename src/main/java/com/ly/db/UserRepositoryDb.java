package com.ly.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.repository.interfaces.UserRepository;

public class UserRepositoryDb implements UserRepository {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "root";

    @Override
    public void insert(User data) {
        String sql = "INSERT INTO Users (login, password, role, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, data.getLogin());
            statement.setString(2, data.getPassword());
            statement.setString(3, data.getRole().name()); // Convert enum Role to String
            statement.setBoolean(4, data.isStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> lister() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> listUserByRole(Role role) {
        String sql = "SELECT * FROM Users WHERE role = ?";
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role.name()); // Convert enum Role to String

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> listUserByStatus(Boolean status) {
        String sql = "SELECT * FROM Users WHERE status = ?";
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserByLoginPasword(String login, String password) {
        String sql = "SELECT * FROM Users WHERE login = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to map ResultSet to User object
    private User mapUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("login"),
                resultSet.getString("password"),
                Role.valueOf(resultSet.getString("role").toUpperCase()), // Convert String to enum Role
                resultSet.getBoolean("status")
        );
    }
}
