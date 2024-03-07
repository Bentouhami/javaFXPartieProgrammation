package be.bentouhami.reservotelapp.Model.DAO.DetailsReservations;

public interface IDetailsReservationDAO {

    boolean getDetailsReservations();
    int writeDetailsReservation(int idReservation, double prixTotalDetailsReservation, int chambre_id);
}
