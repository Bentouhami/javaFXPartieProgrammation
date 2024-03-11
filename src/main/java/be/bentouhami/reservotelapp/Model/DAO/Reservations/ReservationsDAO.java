package be.bentouhami.reservotelapp.Model.DAO.Reservations;

import be.bentouhami.reservotelapp.DataSource.DatabaseConnection;
import be.bentouhami.reservotelapp.Model.BL.Reservation;
import be.bentouhami.reservotelapp.Model.BL.ReservationList;

import java.sql.*;
import java.util.ArrayList;

public class ReservationsDAO implements IReservationsDAO {
    private final PreparedStatement getAllReservations;
    private final PreparedStatement updatePrixReservation;
    private final PreparedStatement getReservationByIdResAndIdCLient;
    private final Connection connexion;
    private final PreparedStatement getReservationsByClientID;
    private final PreparedStatement writeReservations;


    public ReservationsDAO() {
        try {
            this.connexion = DatabaseConnection.getInstance().getConnection();
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
                    "SELECT res.client_id ," +
                            "h.id_hotel, " +
                            "nom_hotel, " +
                            "detR.id_chambre, " +
                            "res.id_reservation," +
                            "res.date_arrive, " +
                            "res.date_depart , " +
                            "res.prix_total_reservation" +
                            " FROM reservations as res" +
                            " INNER JOIN details_reservation as detR ON res.id_reservation = detR.reservation_id" +
                            " INNER JOIN chambres AS ch ON detR.id_chambre = ch.id_chambre" +
                            " INNER JOIN hotels as h ON h.id_hotel = ch.hotel_id WHERE res.client_id = ?"
            );

            this.updatePrixReservation = this.connexion.prepareStatement("UPDATE  reservations" +
                    " SET prix_total_reservation = ? " +
                    " WHERE id_reservation = ?");

            this.getReservationsByClientID = this.connexion.prepareStatement(("SELECT id_reservation, " +
                    " client_id, " +
                    " statut_reservation," +
                    " date_arrive, " +
                    " date_depart, " +
                    " prix_total_reservation," +
                    " date_creation" +
                    " FROM reservations " +
                    " WHERE client_id = ? "));

            this.getReservationByIdResAndIdCLient = this.connexion.prepareStatement(
                    "SELECT prix_total_reservation " +
                            "FROM reservations" +
                            " WHERE id_reservation = ? AND client_id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public ArrayList<String[]> getReservations(int id_client) {

        try {
            this.getAllReservations.setInt(1, id_client);
            ResultSet rs = this.getAllReservations.executeQuery();
            while (rs.next()) {
                String nom_hotel = rs.getString("nom_hotel");
                String prix_total_reservation = String.valueOf(rs.getDouble("prix_total_reservation"));
                String date_arrive = String.valueOf(rs.getDate("date_arrive"));
                String date_depart = String.valueOf(rs.getDate("date_depart"));
                String[] res = new String[4];
                res[0] = nom_hotel;
                res[1] = date_arrive;
                res[2] = date_depart;
                res[3] = prix_total_reservation;
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public void updatePrixTotalReservation(int id_reservation, double prixTotal) {
        try {
            this.updatePrixReservation.setDouble(1, prixTotal);
            this.updatePrixReservation.setInt(2, id_reservation);

            int rowAffected = this.updatePrixReservation.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int writeReservation(int client_id, Date date_arrive, Date date_depart) {
        String status_reservation = "en cours";
        try {
            this.writeReservations.setInt(1, client_id);
            this.writeReservations.setString(2, status_reservation);
            this.writeReservations.setDate(3, date_arrive);
            this.writeReservations.setDate(4, date_depart);
            this.writeReservations.setDouble(5, 0.00);

            ResultSet rs = this.writeReservations.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_reservation");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;

    }

    @Override
    public int writeReservation(Reservation reservation) {
        String status_reservation = "en cours";
        try {
            this.writeReservations.setInt(1, reservation.getClientId());
            this.writeReservations.setString(2, status_reservation);
            this.writeReservations.setDate(3, Date.valueOf(reservation.getDateArrive()));
            this.writeReservations.setDate(4, Date.valueOf(reservation.getDateDepart()));
            this.writeReservations.setDouble(5, reservation.getPrixTotal());

            ResultSet rs = this.writeReservations.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_reservation");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public ReservationList getAllReservationsByClientID(int idClient) {
        ReservationList reservations = new ReservationList();
        try {
            this.getReservationsByClientID.setInt(1, idClient);
            ResultSet rs = this.getReservationsByClientID.executeQuery();

            while (rs.next()) {

                reservations.add(new Reservation(rs.getInt("id_reservation"),
                        rs.getInt("client_id"),
                        rs.getString("statut_reservation"),
                        rs.getDate("date_arrive").toLocalDate(),
                        rs.getDate("date_depart").toLocalDate(),
                        rs.getDouble("prix_total_reservation"),
                        rs.getTimestamp("date_creation")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    @Override
    public double getPrixTotalReservationByIdResAndIdCLient(int idReservation, int idClient) {
        try {
            this.getReservationByIdResAndIdCLient.setInt(1, idReservation);
            this.getReservationByIdResAndIdCLient.setInt(2, idClient);

            ResultSet rs = this.getReservationByIdResAndIdCLient.executeQuery();

            if (rs.next()) {
                return rs.getDouble("prix_total_reservation");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean close() {
        boolean ret = true;

        if (this.getAllReservations != null) {
            try {
                this.getAllReservations.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.updatePrixReservation != null) {
            try {
                this.updatePrixReservation.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getReservationByIdResAndIdCLient != null) {
            try {
                this.getReservationByIdResAndIdCLient.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getReservationsByClientID != null) {
            try {
                this.getReservationsByClientID.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.writeReservations != null) {
            try {
                this.writeReservations.close();
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