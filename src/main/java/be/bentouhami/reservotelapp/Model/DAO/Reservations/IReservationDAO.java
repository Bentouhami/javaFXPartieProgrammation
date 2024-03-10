package be.bentouhami.reservotelapp.Model.DAO.Reservations;

import be.bentouhami.reservotelapp.Model.BL.Reservation;
import be.bentouhami.reservotelapp.Model.BL.ReservationList;

import java.sql.Date;
import java.util.ArrayList;

public interface IReservationDAO {
    ArrayList<String[]> getReservations(int id_client);

    void updatePrixTotalReservation(int id_reservation, double prixTotal);
    int writeReservation(int client_id, Date date_arrive, Date date_depart);
    int writeReservation(Reservation reservation);

    ReservationList getAllReservationsByClientID(int idClient);

    double getPrixTotalReservationByIdResAndIdCLient(int idReservation, int idClient);
}