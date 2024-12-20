package com.ly.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.DemandeDette;
import com.ly.repository.interfaces.DemandeDetteRepository;

public class DemandeDetteRepositoryDB implements DemandeDetteRepository {

    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "root";

    @Override
    public void insert(DemandeDette data) {
        String sql = "INSERT INTO DemandeDette (date, montant, client_id, status) " +
                     "VALUES (?, ?, (SELECT id FROM Client WHERE tel = ?), ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Vérifie si le client et son numéro de téléphone sont valides
            if (data.getClient() == null || data.getClient().getTel() == null) {
                throw new IllegalArgumentException("Le client associé est invalide ou n'a pas de numéro de téléphone !");
            }

            statement.setString(1, data.getDate());
            statement.setFloat(2, data.getMontant());
            statement.setString(3, data.getClient().getTel()); // Recherche le client par son numéro de téléphone
            statement.setBoolean(4, data.isStatus());

            statement.executeUpdate();
            System.out.println("DemandeDette insérée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DemandeDette> lister() {
        String sql = "SELECT * FROM DemandeDette";
        List<DemandeDette> demandes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                demandes.add(mapDemandeDette(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demandes;
    }

    @Override
    public List<DemandeDette> listerDemandeDetteParStatus(boolean status) {
        String sql = "SELECT * FROM DemandeDette WHERE status = ?";
        List<DemandeDette> demandes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandes.add(mapDemandeDette(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demandes;
    }

    // Méthode utilitaire pour mapper un ResultSet à un objet DemandeDette
    private DemandeDette mapDemandeDette(ResultSet resultSet) throws SQLException {
        // Trouve le client correspondant par son ID dans la base
        Client client = getClientById(resultSet.getInt("client_id"));

        return new DemandeDette(
                resultSet.getString("date"),
                resultSet.getFloat("montant"),
                client, // Associe le client récupéré
                resultSet.getBoolean("status")
        );
    }

    // Méthode pour récupérer un client par son ID
    private Client getClientById(int clientId) {
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
