package be.bentouhami.reservotelapp.Model.DAO.Clients;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Client;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class ClientDAO implements IClientDAO {
    private final PreparedStatement getClientByID;
    private final PreparedStatement updateClient;
    private final PreparedStatement validateLogin;
    private final PreparedStatement updateClientAdresse;
    private PreparedStatement updatePassword;
    private PreparedStatement getPhoneByClientId;
    private PreparedStatement addClient;
    private Connection connexion;
    private PreparedStatement getClientByEmail;

    public ClientDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try {
                String sql = "CREATE TABLE IF NOT EXISTS CLIENTS (" +
                        "id_client SERIAL PRIMARY KEY NOT NULL," +
                        "adresse_id INTEGER NOT NULL," +
                        "nom_Client VARCHAR(100) NOT NULL," +
                        "prenom VARCHAR(100) NOT NULL," +
                        "date_Naissance DATE NOT NULL," +
                        "email_Client VARCHAR(250) NOT NULL UNIQUE," +
                        "num_Telephone VARCHAR(20) NOT NULL UNIQUE," +
                        "points_Fidelite INTEGER NOT NULL," +
                        "password VARCHAR(50) NOT NULL," +
                        "CONSTRAINT FK_CLIENT_ADRESSE FOREIGN KEY (adresse_id) " +
                        "REFERENCES ADRESSES (id_adresse))";
                statement.executeUpdate(sql);

            } catch (SQLException e) {
                // La table existe déjà. Log pour le cas où.
                System.out.println(e.getMessage());
            }

            statement.close();

            this.addClient = this.connexion.prepareStatement(
                    "INSERT INTO clients (adresse_id, nom_client, prenom, date_naissance, email_client, num_telephone, points_fidelite, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)".formatted());
            this.getClientByEmail = this.connexion.prepareStatement(
                    "SELECT id_client, adresse_id, " +
                            "nom_client, " +
                            "prenom, " +
                            "date_naissance, " +
                            "email_client, " +
                            "num_telephone, " +
                            " points_fidelite, " +
                            " password FROM CLIENTS" +
                            " WHERE email_client = ? ;");
            this.validateLogin = this.connexion.prepareStatement(
                    "SELECT c.password FROM clients c " +
                            "WHERE email_client = ?");

            this.getClientByID = this.connexion.prepareStatement(
                    "SELECT id_client, adresse_id ," +
                            " nom_client, " +
                            "prenom, " +
                            "date_naissance, " +
                            "email_client, " +
                            "num_telephone, " +
                            "points_fidelite, " +
                            "password FROM CLIENTS " +
                            "WHERE id_client = ?; ");
            this.updateClient = this.connexion.prepareStatement(
                    "UPDATE clients " +
                            "SET " +
                            "date_naissance = ?, " +
                            "email_client = ?, " +
                            "num_telephone = ?, " +
                            "points_fidelite= ?," +
                            "password = ? " +
                            "WHERE id_client = ?;");
            this.updateClientAdresse = this.connexion.prepareStatement(
                    "UPDATE clients " +
                            "SET " +
                            "adresse_id = ? " +
                            "WHERE id_client = ?");
            this.getPhoneByClientId = this.connexion.prepareStatement(
                    "SELECT num_telephone" +
                            " FROM clients" +
                            " WHERE id_client = ?");
            this.updatePassword = this.connexion.prepareStatement(
                    "UPDATE clients" +
                            " set password = ?" +
                            " WHERE id_client = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }// end Constructor

    @Override
    public boolean updateClient(ArrayList<String> clientNewValues) {
        if (!clientNewValues.isEmpty()) {
            // verifier si l'ancien mot de passe que le client a fourni est le bon mot pass stocké dans la DB.
            int id_client = Integer.parseInt(clientNewValues.get(0));

            String dateNaissance = clientNewValues.get(4);
            String email_client = clientNewValues.get(5);
            String num_telephone = clientNewValues.get(6);
            int points_fidelite = Integer.parseInt(clientNewValues.get(7));
            String oldPassword = clientNewValues.get(8);
            String newPassword = clientNewValues.get(9);

            // deuxième vérification de login dans ClientDAO
            boolean isValidPassword = this.verifyClientPassword(id_client, oldPassword);
            if (isValidPassword) {
                // hash new password
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                try {
                    this.updateClient.setDate(1, Date.valueOf(dateNaissance)); // date de naissance
                    this.updateClient.setString(2, email_client); // email
                    this.updateClient.setString(3, num_telephone); // numero de telephone
                    this.updateClient.setInt(4, points_fidelite); // points de fidelite
                    this.updateClient.setString(5, hashedPassword); // mot de passe de client
                    this.updateClient.setInt(6, id_client); // id_client

                    int affectedRows = this.updateClient.executeUpdate();

                    return affectedRows > 0;
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage() + " " + e.getMessage());
                }

            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateCLientAdresse(int id_client, int adresse_id) {
        try {
            this.updateClientAdresse.setInt(1, adresse_id);
            this.updateClientAdresse.setInt(2, id_client);
            return this.updateClientAdresse.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyClientPassword(int clientId, String oldPassword) {
        try {
            this.getClientByID.setInt(1, clientId);
            ResultSet rs = this.getClientByID.executeQuery();
            if (rs.next()) {
                // recuperation le mot de passe de la DB.
                String storedHashedPassword = rs.getString("password");
                // vérifier que l'ancien mot de passe correspond
                return BCrypt.checkpw(oldPassword, storedHashedPassword);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean isValidPhone(int idClient, String numero_telephone) {
        String clientPhoneNum = null;
        try {
            this.getPhoneByClientId.setInt(1, idClient);
            ResultSet rs = this.getPhoneByClientId.executeQuery();
            while (rs.next()) {
                clientPhoneNum = rs.getString("num_telephone");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numero_telephone.equals(clientPhoneNum);
    }

    @Override
    public boolean updatePassword(int idClient, String newPassword) {
        try {
            this.updatePassword.setInt(2, idClient);
            this.updatePassword.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));

            return this.updatePassword.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addClient(
            int idAdresse,
            String nom,
            String prenom,
            String dateNaissance,
            String email,
            String numTel,
            int points_fidelite,
            String password) {


        try {
            this.addClient.setInt(1, idAdresse);
            this.addClient.setString(2, nom);
            this.addClient.setString(3, prenom);
            this.addClient.setDate(4, Date.valueOf(dateNaissance));
            this.addClient.setString(5, email);
            this.addClient.setString(6, numTel);
            this.addClient.setInt(7, points_fidelite);
            // Hacher le mot de passe
            this.addClient.setString(8, BCrypt.hashpw(password, BCrypt.gensalt()));
            int rowsAffected = this.addClient.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public boolean getValidation(String email, String oldPassword) {
        // verification des données
        if ((!email.isBlank() || !email.isEmpty()) && (!oldPassword.isBlank() || !oldPassword.isEmpty())) // verification supplémentaire des donneés
        {
            try {
                // requet pour récupérer le mot de passe qu'est relié à cet email
                this.validateLogin.setString(1, email); // preparation de la requet
                ResultSet rs = this.validateLogin.executeQuery();
                if (rs.next()) {
                    String storedHash = rs.getString("password"); // recuperation de mot de passe
                    if (BCrypt.checkpw(oldPassword, storedHash)) {  // verification si les deux mots de passe sont identiques
                        return true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }// end methode

    @Override
    public Client getClientByEmail(String email) {

        try {
            this.getClientByEmail.setString(1, email);
            ResultSet rs = this.getClientByEmail.executeQuery();

//        id_client ,id_adresse,nom_client, prenom,date_naissance, email_client, num_telephone, points_fidelite, password
            if (rs.next()) {
                return new Client(rs.getInt("id_client"),
                        rs.getInt("adresse_id"),
                        rs.getString("nom_client"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        rs.getString("email_client"),
                        rs.getString("num_telephone"),
                        rs.getInt("points_fidelite"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }// end methode

    @Override
    public Client getClientByID(int id) {
        if (id != -1) {
            try {
                this.getClientByID.setInt(1, id);
                ResultSet rs = this.getClientByID.executeQuery();
                if (rs.next()) {
                    return new Client(rs.getInt("id_client"),
                            rs.getInt("adresse_id"),
                            rs.getString("nom_client"),
                            rs.getString("prenom"),
                            rs.getDate("date_naissance"),
                            rs.getString("email_client"),
                            rs.getString("num_telephone"),
                            rs.getInt("points_fidelite"),
                            rs.getString("password"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }
}// end classe