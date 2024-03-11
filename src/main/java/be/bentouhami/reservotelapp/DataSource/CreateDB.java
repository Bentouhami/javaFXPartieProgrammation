package be.bentouhami.reservotelapp.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    public static void initializeDatabase() {
        String createDatabaseSql = "CREATE DATABASE maximReservotel";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(createDatabaseSql);
                System.out.println("Création de la base de données maximReservotel réussie.");
            } catch (SQLException e) {
                System.out.println("Une erreur est survenue lors de la création de la base de données : " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Impossible de se connecter à la base de données : " + e.getMessage());
        }

    }
}
