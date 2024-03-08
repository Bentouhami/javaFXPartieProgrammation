package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Adresse;

import java.sql.*;
import java.util.ArrayList;

public class AdresseDAO implements IAdressesDAO {
    private final PreparedStatement getAllVillesByPays;
    private final PreparedStatement addAdresse;
    private PreparedStatement getAdresseByDatas;
    Connection connexion;
    PreparedStatement getAdresseByID;
    PreparedStatement updateAdresse;
    PreparedStatement getAllPays;

    public AdresseDAO() throws SQLException {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try {
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

            this.addAdresse = this.connexion.prepareStatement
                    (
                            "INSERT INTO Adresses " +
                                    "(rue, numero, boite, ville, codepostal, pays) " +
                                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_adresse "
                    );
            this.updateAdresse = this.connexion.prepareStatement(
                    "UPDATE adresses " +
                            "SET " +
                            "rue = ?, " +
                            "numero = ?, " +
                            "boite = ?, " +
                            "ville = ?, " +
                            "codepostal = ?, " +
                            "pays = ? " +
                            "WHERE id_adresse = ?;");

            this.getAllPays = this.connexion.prepareStatement(
                    "SELECT DISTINCT pays FROM adresses" +
                            " ORDER BY pays");
            this.getAllVillesByPays = this.connexion.prepareStatement(
                    "SELECT DISTINCT a.ville FROM adresses a WHERE pays = ? " +
                            " ORDER BY ville"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateAdresse_dao(ArrayList<String> adresseNewValues) {

        if (!adresseNewValues.isEmpty()) {
            try {
                String rue = adresseNewValues.get(10);
                String numRue = adresseNewValues.get(11);
                String boite = adresseNewValues.get(12);
                String ville = adresseNewValues.get(13);
                String codepostal = adresseNewValues.get(14);
                String pays = adresseNewValues.get(15);
                int id_adresse = Integer.parseInt(adresseNewValues.get(1));

                this.updateAdresse.setString(1, rue); // rue
                this.updateAdresse.setString(2, numRue); // numRue
                this.updateAdresse.setString(3, boite); // boite
                this.updateAdresse.setString(4, ville); // Ville
                this.updateAdresse.setString(5, codepostal); // codepostal
                this.updateAdresse.setString(6, pays); // pays
                this.updateAdresse.setInt(7, id_adresse); // id_adresse

                int affectedRows = this.updateAdresse.executeUpdate();

                return affectedRows > 0;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;

    }

    @Override
    public boolean close_dao() {
        return false;
    }


    @Override
    public int addAdresse_dao(String rue,
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
            ResultSet rs = this.addAdresse.executeQuery();
            // Exécutez la requête
            // Récupérez l'ID généré
            if (rs.next()) {
                idAdresse = rs.getInt("id_adresse");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idAdresse;
    }

    @Override
    public Adresse getAdresseByID(int adresseId) {
        Adresse adresseClient = null;
        try {
            this.getAdresseByID.setInt(1, adresseId);
            ResultSet rs = this.getAdresseByID.executeQuery();
            if (rs.next()) {
                adresseClient = new Adresse(rs.getInt("id_adresse"),
                        rs.getString("rue"),
                        rs.getString("numero"),
                        rs.getString("boite"),
                        rs.getString("ville"),
                        rs.getString("codepostal"),
                        rs.getString("pays"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return adresseClient;
    }

    @Override
    public ArrayList<String> getAllPays() {
        ArrayList<String> pays = new ArrayList<>();
        try {
            ResultSet rs = this.getAllPays.executeQuery();

            while (rs.next()) {
                pays.add(rs.getString("pays"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pays;
    }

    @Override
    public ArrayList<String> getAllVilles(String pays) {

        ArrayList<String> villes = new ArrayList<>();
        try {
            this.getAllVillesByPays.setString(1, pays);
            ResultSet rs = this.getAllVillesByPays.executeQuery();

            while (rs.next()) {
                villes.add(rs.getString("ville"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return villes;
    }
}
