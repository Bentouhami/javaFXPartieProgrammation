package be.bentouhami.reservotelapp.Model.DAO.DetailsReservations;

public interface IDetailsReservationDAO {

    boolean getDetailsReservations();
    int writeDetailsReservations(int idReservation, double prixChambre, int idChambre);
}
