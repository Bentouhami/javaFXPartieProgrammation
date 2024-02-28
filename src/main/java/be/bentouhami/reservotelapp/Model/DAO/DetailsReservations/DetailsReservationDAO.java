package be.bentouhami.reservotelapp.Model.DAO.DetailsReservations;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DetailsReservationDAO implements IDetailsReservationDAO {
    private Connection connexion;
    private PreparedStatement getDetailsReservations;
    private PreparedStatement writeDetailsReservations;

    public DetailsReservationDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = this.connexion.createStatement();
            statement.executeUpdate("create table if not exists details_reservation " +
                    "(" +
                    "    id_details_reservation serial primary key, " +
                    "    client_id integer not null, " +
                    "    reservation_id integer not null " +
                    "        constraint fk_details_reservation_reservation " +
                    "            references reservations, " +
                    "    prix_total_chambre     numeric(10, 2), " +
                    "    id_chambre             integer " +
                    "        constraint fk_details_reservation_chambre " +
                    "            references chambres " +
                    ");");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getDetailsReservations() {
        return false;
    }

    @Override
    public boolean writeDetailsReservations() {
        return false;
    }
}
