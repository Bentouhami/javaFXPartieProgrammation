package be.bentouhami.reservotelapp.Model.DAO.Clients;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Client;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class ClientDAO implements IClientDAO {
    private final PreparedStatement addClient;
    private final Connection connection;
    private final PreparedStatement getClient;

    public ClientDAO() throws Exception {
        try {
            this.connection = DataSource.getInstance().getConnection();
            Statement statement = connection.createStatement();


            try {
                String sql =  "CREATE TABLE IF NOT EXISTS CLIENTS ("+
                        "id_client SERIAL PRIMARY KEY NOT NULL,"+
                        "adresse_id INTEGER NOT NULL,"+
                        "nom_Client VARCHAR(100) NOT NULL,"+
                        "prenom VARCHAR(100) NOT NULL,"+
                        "date_Naissance DATE NOT NULL,"+
                        "email_Client VARCHAR(250) NOT NULL UNIQUE,"+
                        "num_Telephone VARCHAR(20) NOT NULL UNIQUE,"+
                        "points_Fidelite INTEGER NOT NULL,"+
                        "password VARCHAR(50) NOT NULL,"+
                        "CONSTRAINT FK_CLIENT_ADRESSE FOREIGN KEY (adresse_id) " +
                        "REFERENCES ADRESSES (id_adresse))";
                statement.executeUpdate(sql);

            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

            statement.close();
            this.getClient = this.connection.prepareStatement("SELECT * FROM clients" +
                    " WHERE email_client = ? AND password = ?;");
            this.addClient = this.connection.prepareStatement(
                    "INSERT INTO clients (adresse_id," +
                            " nom_client, " +
                            "prenom, " +
                            "date_naissance, " +
                            "email_client, " +
                            "num_telephone, " +
                            "points_fidelite, " +
                            "password) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean addClient(
                                int idAdresse,
                                String nom,
                                String prenom,
                                String dateNaissance,
                                String numTel,
                                String email,
                                int points_fidelite,
                                String password) {


       try {
           this.addClient.setInt(1,idAdresse);
           this.addClient.setString(2,nom);
           this.addClient.setString(3,prenom);
           this.addClient.setDate(4, Date.valueOf(dateNaissance));
           this.addClient.setString(5,numTel);
           this.addClient.setString(6,email);
           this.addClient.setInt(7,points_fidelite);
           // Hacher le mot de passe
           this.addClient.setString(8, BCrypt.hashpw(password, BCrypt.gensalt()));
           int rowsAffected = this.addClient.executeUpdate();

           return rowsAffected > 0;

           } catch (SQLException ex) {
           throw new RuntimeException(ex);
       }
    }


    @Override
    public Client getClientByEmail(String email, String password){
        try {
            this.getClient.setString(1 , email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = this.getClient.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        id_client ,id_adresse,nom_client, prenom,date_naissance, email_client, num_telephone, points_fidelite, password

        try {
            if(rs.next()){
            String storedHash = rs.getString("password");
                if(email.equals(rs.getString("email_client")) &&
                        BCrypt.checkpw(password, storedHash)){
                    try {
                        return new Client(rs.getInt("id_client"),
                                rs.getInt("id_adresse"),
                                rs.getString("nom_client"),
                                rs.getString("prenom"),
                                rs.getDate("date_naissance"),
                                rs.getString("email_client"),
                                rs.getString("num_telephone"),
                                rs.getInt("points_fidelite"),
                                storedHash);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
