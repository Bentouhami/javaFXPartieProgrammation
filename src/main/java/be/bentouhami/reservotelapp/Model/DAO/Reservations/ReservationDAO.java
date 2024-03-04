package be.bentouhami.reservotelapp.Model.DAO.Reservations;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Reservation;
import be.bentouhami.reservotelapp.Model.BL.ReservationList;

import java.sql.*;

public class ReservationDAO implements IReservationDAO {
    private final PreparedStatement getAllReservations;
    private final PreparedStatement updateprixReservation;
    private Connection connexion;
    private PreparedStatement getReservations;
    private PreparedStatement writeReservations;


    public ReservationDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = this.connexion.createStatement();
            try {

                statement.executeUpdate("create table if not exists reservations " +
                        "( id_reservation serial primary key, " +
                        "    client_id              integer        not null, " +
                        "    statut_reservation     varchar(50)    not null, " +
                        "    date_arrive            date           not null, " +
                        "    date_depart            date           not null, " +
                        "    prix_total_reservation numeric(10, 2) not null " +
                        ");");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();
            this.writeReservations = this.connexion.prepareStatement("INSERT INTO reservations " +
                    "(client_id," +
                    " statut_reservation," +
                    " date_arrive," +
                    " date_depart," +
                    " prix_total_reservation )" +
                    " VALUES (?,?,?,?,?)" +
                    " RETURNING id_reservation");

            this.getAllReservations = this.connexion.prepareStatement(
                    "SELECT nom_hotel, res.prix_total_reservation, res.date_arrive, res.date_depart , res.prix_total_reservation" +
                            " FROM reservations as res" +
                            " INNER JOIN details_reservation as detR ON res.id_reservation = detR.reservation_id" +
                            " INNER JOIN chambres AS ch ON detR.id_chambre = ch.id_chambre" +
                            " INNER JOIN HOTELS as h ON h.id_hotel = ch.hotel_id WHERE res.client_id = ?"
            );

            this.updateprixReservation = this.connexion.prepareStatement("UPDATE  reservations SET prix_total_reservation = ? WHERE id_reservation = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public ReservationList getReservations(int id_client) {
        ReservationList reservationList = new ReservationList();
        try {
            this.getAllReservations.setInt(1, id_client);
            ResultSet rs = this.getAllReservations.executeQuery();
            while (rs.next()) {
                reservationList.add(new Reservation());
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return reservationList;
    }
    @Override
    public int writeReservation(int clientId, Date dateArriver, Date dateDepart) {
        int id_reservation = 0;
        try {
            this.writeReservations.setInt(1, clientId);
            this.writeReservations.setString(2, "en cours");
            this.writeReservations.setDate(3, dateArriver);
            this.writeReservations.setDate(4, dateDepart);
            this.writeReservations.setDouble(5, 0.00);

            ResultSet rs = this.writeReservations.executeQuery();

            if (rs.next()) {
                id_reservation = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_reservation;
    }

    @Override
    public boolean updatePrixTotalReservation(int id_reservation, double prixTotal) {
        try {
            this.updateprixReservation.setDouble(1, prixTotal);
            this.updateprixReservation.setInt(2, id_reservation);

            int rowsAffected = this.updateprixReservation.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
