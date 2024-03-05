package be.bentouhami.reservotelapp.Model.DAO.Reservations;

import java.sql.Date;
import java.util.ArrayList;

public interface IReservationDAO {
    ArrayList<String[]> getReservations(int id_client);

    int writeReservation(int clientId, Date dateArriver, Date dateDepart);


    boolean updatePrixTotalReservation(int id_reservation, double prixTotal);
}
