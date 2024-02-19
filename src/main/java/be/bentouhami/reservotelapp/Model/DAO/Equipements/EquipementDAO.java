package be.bentouhami.reservotelapp.Model.DAO.Equipements;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Equipement;

import java.sql.*;
import java.util.ArrayList;

public class EquipementDAO implements IEquipementDAO {

    private Connection connexion;
    private PreparedStatement getAllEquipements;
    private PreparedStatement getHotelEquipemntsById;

    public EquipementDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Equipements" +
                        "(id_equipement SERIAL PRIMARY KEY," +
                        "equipement varchar(100) NOT NULL," +
                        " description_equipement TEXT NOT NULL)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();
            this.getAllEquipements = this.connexion.prepareStatement
                    ("SELECT DISTINCT equipement FROM equipements" +
                            " ORDER BY equipement");
            this.getHotelEquipemntsById = this.connexion.prepareStatement(
                    "SELECT e.id_equipement, e.equipement, e.description_equipement " +
                            "    FROM equipements_hotel eh " +
                            "         JOIN equipements e ON eh.id_equipement = e.id_equipement " +
                            "WHERE eh.id_hotel = ?;"
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getAllEquipements() {
        ArrayList<String> eq = new ArrayList<>();
        try {
            ResultSet rs = this.getAllEquipements.executeQuery();
            while (rs.next()) {
                eq.add(rs.getString("equipement"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eq;
    }

    @Override
    public ArrayList<Equipement> getHotelEquipementsByHotelId(String hotelId) {
        ArrayList<Equipement> hotelEquipemets = new ArrayList<>();
        try {
            this.getHotelEquipemntsById.setInt(1, Integer.parseInt(hotelId));
            ResultSet rs = this.getHotelEquipemntsById.executeQuery();
            while (rs.next()) {
                hotelEquipemets.add(new Equipement(rs.getInt("id_equipement"),
                        rs.getString("equipement"),
                        rs.getString("description_equipement")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelEquipemets;
    }
}
