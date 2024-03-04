package be.bentouhami.reservotelapp.Model.DAO.Reservations;

import be.bentouhami.reservotelapp.Model.BL.ReservationList;

import java.sql.Date;

public interface IReservationDAO {
    ReservationList getReservations(int id_client);

    int writeReservation(int clientId, Date dateArriver, Date dateDepart);


    boolean updatePrixTotalReservation(int id_reservation, double prixTotal);
}
