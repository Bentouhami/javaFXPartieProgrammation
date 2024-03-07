package be.bentouhami.reservotelapp.Model.DAO.DetailsReservations;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.sql.*;

public class DetailsReservationDAO implements IDetailsReservationDAO {
    private Connection connexion;
    private PreparedStatement getDetailsReservations;
    private PreparedStatement writeDetailsReservation;

    public DetailsReservationDAO() {
        try {
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

                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.writeDetailsReservation =
                    this.connexion.prepareStatement("INSERT INTO details_reservation (reservation_id, prix_total_details_reservation, id_chambre)" +
                    " VALUES (?,?,?) " +
                    "RETURNING id_details_reservation; ");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getDetailsReservations() {
        return false;
    }

    @Override
    public int writeDetailsReservation(int idReservation,
                                        double prixTotalDetailsReservation,
                                        int idChambre) {
        int id_details_reservation = 0;
        try {
            this.writeDetailsReservation.setInt(1, idReservation);
            this.writeDetailsReservation.setDouble(2, prixTotalDetailsReservation);
            this.writeDetailsReservation.setDouble(3, idChambre);


            ResultSet rs = this.writeDetailsReservation.executeQuery();

            if(rs.next()){
                id_details_reservation = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_details_reservation;
    }


}
