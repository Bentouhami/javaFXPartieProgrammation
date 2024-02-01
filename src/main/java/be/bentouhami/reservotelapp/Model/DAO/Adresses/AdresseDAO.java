package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.io.IOException;
import java.sql.*;

public class AdresseDAO implements IAdressesDAO {
    private PreparedStatement addAdresse;
    private PreparedStatement getAdresseByDatas;
    Connection connexion;
    PreparedStatement getAdresse;
    PreparedStatement editAdresse;

    public AdresseDAO() throws SQLException {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try{
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Adresse" +
                        "(id_adresse SERIAL PRIMARY KEY," +
                        " rue varchar(250) not null," +
                        " numero varchar(10) not null," +
                        " boite varchar(100) not null, " +
                        " ville varchar(20)  not null," +
                        " codepostal varchar(15)  not null, " +
                        "pays varchar(15) not null )");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            statement.close();
            this.getAdresse = this.connexion.prepareStatement(
                    "SELECT rue, " +
                            "numero," +
                            " boite, " +
                            "ville, " +
                            "codePostal," +
                            " pays FROM Adresses" +
                            " WHERE id_adersse = ?"
            );

           this.addAdresse  = connexion.prepareStatement
                    (
                        "INSERT INTO Adresses " +
                            "(rue, numero, boite, ville, codepostal, pays) " +
                            "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_adresse "
                    );


        } catch (SQLException | IOException e){
            throw new RuntimeException(e);
        }

    }


    @Override
    public boolean getAdresse() {



        return false;
    }



    @Override
    public boolean editAdresse() {

        return false;
    }

    @Override
    public boolean close() {
        return false;
    }


    @Override
    public int addAdresse(String rue,
                             String numero,
                             String boite,
                             String codePostal,
                             String ville,
                             String pays) {


        int idAdresse = -1;

        try {
            this.connexion = DataSource.getInstance().getConnection();
            this.addAdresse.setString(1, rue);
            this.addAdresse.setString(2, numero);
            this.addAdresse.setString(3, boite);
            this.addAdresse.setString(4, codePostal);
            this.addAdresse.setString(5, ville);
            this.addAdresse.setString(6, pays);

            // Exécutez la requête
            try (ResultSet rs = this.addAdresse.executeQuery()) {
                // Récupérez l'ID généré
                if (rs.next()) {
                    idAdresse = rs.getInt("id_adresse");
                }
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return idAdresse;


    }
}
