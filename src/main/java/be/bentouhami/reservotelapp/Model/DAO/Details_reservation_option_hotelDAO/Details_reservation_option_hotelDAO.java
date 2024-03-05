package be.bentouhami.reservotelapp.Model.DAO.Details_reservation_option_hotelDAO;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Details_reservation_option_hotelDAO implements IDetails_reservation_option_hotelDAO {
    private Connection connexion;
    private PreparedStatement getAll;
    private PreparedStatement update;
    private PreparedStatement writeDetailsReservationOptionHotel;


    public Details_reservation_option_hotelDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = this.connexion.createStatement();
            try {
                statement.execute("CREATE TABLE IF NOT EXISTS details_reservation_option_hotel " +
                        "( id_option_hotel       INTEGER NOT NULL, " +
                        "    details_reservation_id INTEGER NOT NULL, " +
                        "    CONSTRAINT fk_details_reservation_option_hotel " +
                        "        FOREIGN KEY (id_option_hotel) " +
                        "        REFERENCES option_hotel (id_option_hotel), " +
                        "    CONSTRAINT fk_details_reservation_details " +
                        "        FOREIGN KEY (details_reservation_id) " +
                        "        REFERENCES details_reservation (id_details_reservation), " +
                        "    PRIMARY KEY (id_option_hotel, details_reservation_id))");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();

            this.writeDetailsReservationOptionHotel = this.connexion.prepareStatement(
                    "INSERT INTO details_reservation_option_hotel (id_option_hotel, details_reservation_id)" +
                            " VALUES (?,?);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAllDetailsReservationOptionHotel() {

    }

    @Override
    public void writeDetailsReservationOptionHotel(int idOptionHotel, int idDetailsReservation) {
        try {
            this.writeDetailsReservationOptionHotel.setInt(1, idOptionHotel);
            this.writeDetailsReservationOptionHotel.setInt(2, idDetailsReservation);

            int rowsAffected = this.writeDetailsReservationOptionHotel.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
