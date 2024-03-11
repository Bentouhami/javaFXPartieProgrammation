package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.DataSource.DatabaseConnection;
import be.bentouhami.reservotelapp.Model.BL.Adresse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdressesDAO implements IAdressesDAO {
    private final PreparedStatement getAllVillesByPays;
    private final PreparedStatement addAdresse;
    private final PreparedStatement insertAdresse;
    private final PreparedStatement getAdressesIds;
    private final PreparedStatement getAdresses;
    private Connection connexion;
    private final PreparedStatement getAdresseByID;
    private final PreparedStatement updateAdresse;
    private final PreparedStatement getAllPays;


    public AdressesDAO() throws SQLException {
        try {
            this.connexion = DatabaseConnection.getInstance().getConnection();
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

            this.insertAdresse = this.connexion.prepareStatement("INSERT INTO adresses (rue, numero, boite, ville, codepostal, pays) VALUES (?, ?, ?, ?, ?, ?)");
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
                            " ORDER BY ville");

            this.getAdressesIds = this.connexion.prepareStatement("SELECT id_adresse FROM adresses");

            this.getAdresses = this.connexion.prepareStatement(
                    """
                            SELECT id_adresse,
                                    ville,
                                    pays
                                  FROM adresses
                                """);


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
    public int addAdresse_dao(String rue,
                              String numero,
                              String boite,
                              String ville,
                              String codePostal,
                              String pays) {
        int idAdresse = -1;
        try {
            this.connexion = DatabaseConnection.getInstance().getConnection();
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

    @Override
    public void insert(Adresse adresse) {

        try {
            // les paramètres pour la requête d'insertion à partir de l'objet adresse
            this.insertAdresse.setString(1, adresse.getRue());
            this.insertAdresse.setString(2, adresse.getNumero());
            this.insertAdresse.setString(3, adresse.getBoite());
            this.insertAdresse.setString(4, adresse.getVille());
            this.insertAdresse.setString(5, adresse.getCodePostal());
            this.insertAdresse.setString(6, adresse.getPays());

            // Exécuter la requête d'insertion
            int rowsAffected = this.insertAdresse.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getAdressesId() {
        List<Integer> adressesIds = new ArrayList<>();
        try {
            ResultSet rs = this.getAdressesIds.executeQuery();
            while (rs.next()) {
                adressesIds.add(rs.getInt("id_adresse"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return adressesIds;
    }

    @Override
    public List<Adresse> getadresses() {

        List<Adresse> adresses = new ArrayList<>();
        try {
            ResultSet rs = this.getAdresses.executeQuery();
            while (rs.next()) {
                adresses.add(new Adresse(rs.getInt("id_adresse"),
                        rs.getString("ville"),
                        rs.getString("pays")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return adresses;
    }


//    @Override
//    public boolean close() {
//        boolean isSuccessful = true; // Pour suivre si toutes les fermetures ont réussi
//
//        // Fermer getAllVillesByPays
//        if (this.getAllVillesByPays != null) {
//            try {
//                this.getAllVillesByPays.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer addAdresse
//        if (this.addAdresse != null) {
//            try {
//                this.addAdresse.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer getAdresseByID
//        if (this.getAdresseByID != null) {
//            try {
//                this.getAdresseByID.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer updateAdresse
//        if (this.updateAdresse != null) {
//            try {
//                this.updateAdresse.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer getAllPays
//        if (this.getAllPays != null) {
//            try {
//                this.getAllPays.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer insertAdresse
//        if (this.insertAdresse != null) {
//            try {
//                this.insertAdresse.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer getAdressesIds
//        if (this.getAdressesIds != null) {
//            try {
//                this.getAdressesIds.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//        // Fermer getAdresses
//        if (this.getAdresses != null) {
//            try {
//                this.getAdresses.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        // Fermer la connexion
//        if (this.connexion != null) {
//            try {
//                this.connexion.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                isSuccessful = false;
//            }
//        }
//
//        return isSuccessful; // Retourne le résultat de la fermeture des ressources
//    }


}
