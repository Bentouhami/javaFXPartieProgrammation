package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Adresse;

import java.sql.*;

public class AdresseDAO implements IAdressesDAO {
    private PreparedStatement addAdresse;
    private PreparedStatement getAdresseByDatas;
    Connection connexion;
    PreparedStatement getAdresseByID;
    PreparedStatement editAdresse;

    public AdresseDAO() throws SQLException {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try{
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Adresses" +
                        "(id_adresse SERIAL PRIMARY KEY," +
                        " rue varchar(250) not null," +
                        " numero varchar(10) not null," +
                        " boite varchar(100) not null, " +
                        " codepostal varchar(15)  not null, " +
                        " ville varchar(20)  not null," +
                        " pays varchar(15) not null )");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            statement.close();
            this.getAdresseByID = this.connexion.prepareStatement(
                    "SELECT ad.* " +
                            "FROM Adresses ad " +
                            " WHERE id_adresse = ?"
            );

           this.addAdresse  = connexion.prepareStatement
                    (
                        "INSERT INTO Adresses " +
                            "(rue, numero, boite, ville, codepostal, pays) " +
                            "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_adresse "
                    );


        } catch (SQLException e){
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
                             String ville,
                             String codePostal,
                             String pays) {


        int idAdresse = -1;

        try {
            this.connexion = DataSource.getInstance().getConnection();
            this.addAdresse.setString(1, rue);
            this.addAdresse.setString(2, numero);
            this.addAdresse.setString(3, boite);
            this.addAdresse.setString(4, ville);
            this.addAdresse.setString(5, codePostal);
            this.addAdresse.setString(6, pays);

            // Exécutez la requête
            try (ResultSet rs = this.addAdresse.executeQuery()) {
                // Récupérez l'ID généré
                if (rs.next()) {
                    idAdresse = rs.getInt("id_adresse");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return idAdresse;


    }

    @Override
    public Adresse getAdresseByID(int adresseId) {

            try {
                this.getAdresseByID.setInt(1, adresseId);
                ResultSet rs = this.getAdresseByID.executeQuery();
                if(rs.next()){
                    return new Adresse(rs.getInt("id_adresse"),
                            rs.getString("rue"),
                            rs.getString("numero"),
                            rs.getString("boite"),
                            rs.getString("ville"),
                            rs.getString("codepostal"),
                            rs.getString("pays"));
                }

            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        return null;
    }
}
