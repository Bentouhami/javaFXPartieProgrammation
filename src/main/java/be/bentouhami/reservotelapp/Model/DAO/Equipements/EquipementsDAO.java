package be.bentouhami.reservotelapp.Model.DAO.Equipements;

import be.bentouhami.reservotelapp.DataSource.DatabaseConnection;
import be.bentouhami.reservotelapp.Model.BL.Equipement;
import be.bentouhami.reservotelapp.Model.BL.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipementsDAO implements IEquipementsDAO {

    private final Connection connexion;
    private final PreparedStatement getAllEquipements;
    private final PreparedStatement getHotelEquipemntsById;
    private final PreparedStatement insert;
    private final PreparedStatement getEquipements;
    private final PreparedStatement insertEquipementHotel;

    public EquipementsDAO() {
        try {
            this.connexion = DatabaseConnection.getInstance().getConnection();
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

            this.insert = this.connexion.prepareStatement("INSERT INTO equipements (equipement, description_equipement) VALUES (?, ?);");

            this.getAllEquipements = this.connexion.prepareStatement
                    ("SELECT DISTINCT equipement FROM equipements" +
                            " ORDER BY equipement");
            this.getHotelEquipemntsById = this.connexion.prepareStatement(
                    "SELECT e.id_equipement, e.equipement, e.description_equipement " +
                            "    FROM equipements_hotel eh " +
                            "         JOIN equipements e ON eh.id_equipement = e.id_equipement " +
                            "WHERE eh.id_hotel = ?;"
            );

            this.getEquipements = this.connexion.prepareStatement(
                    """
                                 SELECT id_equipement,
                                    equipement,
                                    description_equipement
                                     FROM equipements

                            """
            );

            this.insertEquipementHotel = this.connexion.prepareStatement(
                    """
                            INSERT INTO equipements_hotel (id_hotel, id_equipement) VALUES (?, ?)
                        """
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void insert(Equipement equipement) {
        try {
            this.insert.setString(1, equipement.getEquipement());
            this.insert.setString(2, equipement.getDescription_equipement());

            this.insert.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Equipement> getEquipements() {
        List<Equipement> equipements = new ArrayList<>();
        try {
            ResultSet rs = this.getEquipements.executeQuery();
            while (rs.next()) {
                equipements.add(new Equipement(rs.getInt("id_equipement"),
                        rs.getString("equipement"),
                        rs.getString("description_equipement")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipements;
    }

    @Override
    public void insertEquipementHotel(int idHotel, int idEquipement) {
        try {
            this.insertEquipementHotel.setInt(1, idHotel);
            this.insertEquipementHotel.setInt(2, idEquipement);

            int rowsAfected = this.insertEquipementHotel.executeUpdate();
        } catch (Exception e) {
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
    public ArrayList<Equipement> getHotelEquipementsByHotelId(Hotel hotel) {
        ArrayList<Equipement> hotelEquipemets = new ArrayList<>();
        try {
            this.getHotelEquipemntsById.setInt(1, Integer.parseInt(String.valueOf(hotel.getIdHotel())));
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

    @Override
    public boolean close() {
        boolean ret = true;

        if (this.getAllEquipements != null) {
            try {
                this.getAllEquipements.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.insertEquipementHotel != null) {
            try {
                this.insertEquipementHotel.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }


        if (this.getEquipements != null) {
            try {
                this.getEquipements.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getHotelEquipemntsById != null) {
            try {
                this.getHotelEquipemntsById.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.insert != null) {
            try {
                this.insert.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.connexion != null) {
            try {
                this.connexion.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        return ret;
    }


}
