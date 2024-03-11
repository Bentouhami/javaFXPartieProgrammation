package be.bentouhami.reservotelapp.Model.DAO.Hotels;

import be.bentouhami.reservotelapp.DataSource.DatabaseConnection;
import be.bentouhami.reservotelapp.Model.BL.Hotel;
import be.bentouhami.reservotelapp.Model.BL.HotelList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelsDAO implements IHotelsDAO {

    private final PreparedStatement getAllPrix;
    private final PreparedStatement getHotelById;
    private final Connection connexion;
    private final PreparedStatement getHotels;
    private final PreparedStatement insert;
    private final PreparedStatement getAllHotels;

    public HotelsDAO() throws SQLException {
        try {
            this.connexion = DatabaseConnection.getInstance().getConnection();
            Statement statement = connexion.createStatement();

            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Hotels" +
                        "(id_hotel SERIAL " +
                        "        constraint pk_hotel" +
                        "            primary key," +
                        "    id_adresse integer NOT NULL," +
                        "    nom_hotel varchar(20)   not null," +
                        "    etoils integer not null" +
                        "        constraint ckc_etoils_hotel" +
                        "            check ((etoils >= 1) AND (etoils <= 5))," +
                        "    description_hotel    varchar(255)  not null," +
                        "    prix_chambre_minimum numeric(5, 2) not null," +
                        "    nombre_chambre       integer       not null," +
                        "    contact_telephone    varchar(20)   not null," +
                        "    photo_hotel          text          not null," +
                        "    contact_email        varchar(150)  not null" +
                        ");");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();

            this.getAllHotels = this.connexion.prepareStatement(
                    """
                                SELECT id_hotel, etoiles FROM hotels;
                            """
            );
            this.insert = this.connexion.prepareStatement(
                    """
                                INSERT INTO hotels (adresse_id,
                                    nom_hotel,
                                    etoiles,
                                    description_hotel,
                                    photo_hotel,
                                    prix_chambre_min,
                                    nombre_chambre,
                                    contact_telephone,
                                    contact_email) VALUES (?,?,?,?,?,?,?,?,?)
                            """);
            this.getHotels = this.connexion.prepareStatement("SELECT h.* FROM hotels h " +
                    "JOIN adresses a ON h.adresse_id = a.id_adresse" +
                    " WHERE a.ville = ?;");
            this.getAllPrix = this.connexion.prepareStatement("SELECT DISTINCT prix_chambre_min FROM hotels" +
                    " ORDER BY prix_chambre_min;");

            this.getHotelById = this.connexion.prepareStatement("SELECT id_hotel, adresse_id, nom_hotel, etoiles, description_hotel, photo_hotel, prix_chambre_min, nombre_chambre, contact_telephone, contact_email " +
                    "FROM hotels WHERE id_hotel = ?");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// end constructor


    @Override
    public HotelList getHotels(String ville) {
        HotelList hotels = new HotelList();
        try {
            this.getHotels.setString(1, ville);
            ResultSet rs = getHotels.executeQuery();

            while (rs.next()) {

                // addresse_id, nom_hotel, etoiles, description_hotel, photo_hotel, prix_chambre_min, nombre_chambre, contact_telephone, contact_email
                hotels.add(new Hotel(rs.getInt("id_hotel"), // id hotel
                        rs.getInt("adresse_id"), // adresse_id
                        rs.getString("nom_hotel"), // nom hotel
                        rs.getInt("etoiles"), // etoiles
                        rs.getString("description_hotel"), // desciption
                        rs.getString("photo_hotel"), // photo
                        rs.getDouble("prix_chambre_min"), // prix chambre min
                        rs.getInt("nombre_chambre"), // nombre des chambres
                        rs.getString("contact_telephone"), // contactTelephone
                        rs.getString("contact_email") // contactEmail
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    @Override
    public ArrayList<String> getAllPrix() {
        ArrayList<String> prix = new ArrayList<>();
        try {
            ResultSet rs = this.getAllPrix.executeQuery();
            while (rs.next()) {
                prix.add(String.valueOf(rs.getDouble("prix_chambre_min")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prix;
    }

    @Override
    public Hotel getHotelById(String hotelId) {

        try {
            this.getHotelById.setInt(1, Integer.parseInt(hotelId));
            ResultSet rs = this.getHotelById.executeQuery();
            if (rs.next()) {
                return new Hotel(rs.getInt("id_hotel"),
                        rs.getInt("adresse_id"),
                        rs.getString("nom_hotel"),
                        rs.getInt("etoiles"),
                        rs.getString("description_hotel"),
                        rs.getString("photo_hotel"),
                        rs.getInt("prix_chambre_min"),
                        rs.getInt("nombre_chambre"),
                        rs.getString("contact_telephone"),
                        rs.getString("contact_email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(Hotel hotel) {
        try {
            this.insert.setInt(1, hotel.getAdresse_id());
            this.insert.setString(2, hotel.getNom());
            this.insert.setInt(3, hotel.getEtoils());
            this.insert.setString(4, hotel.getDescrition());
            this.insert.setString(5, hotel.getPhoto());
            this.insert.setDouble(6, hotel.getPrixChambreMin());
            this.insert.setInt(7, hotel.getNombreChambres());
            this.insert.setString(8, hotel.getContactTelephone());
            this.insert.setString(9, hotel.getContactEmail());

            int rowsAffected = this.insert.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        try {
            ResultSet rs = this.getAllHotels.executeQuery();
            while (rs.next()) {
                hotels.add(new Hotel(rs.getInt("id_hotel"), rs.getInt("etoiles")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hotels;
    }

    @Override
    public boolean close() {
        boolean ret = true;

        if (this.getAllPrix != null) {
            try {
                this.getAllPrix.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.getAllHotels != null) {
            try {
                this.getAllHotels.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getHotelById != null) {
            try {
                this.getHotelById.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getHotels != null) {
            try {
                this.getHotels.close();
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
