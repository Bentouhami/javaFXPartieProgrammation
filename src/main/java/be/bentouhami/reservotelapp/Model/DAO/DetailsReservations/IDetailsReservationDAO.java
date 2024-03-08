package be.bentouhami.reservotelapp.Model.DAO.DetailsReservations;

import be.bentouhami.reservotelapp.Model.BL.DetailsReservation;

import java.util.ArrayList;

public interface IDetailsReservationDAO {

    boolean getDetailsReservations();
    int writeDetailsReservation(int idReservation, double prixTotalDetailsReservation, int chambre_id);

    ArrayList<DetailsReservation> getDetailsReservationsListByReservationID(int idReservation);
}
