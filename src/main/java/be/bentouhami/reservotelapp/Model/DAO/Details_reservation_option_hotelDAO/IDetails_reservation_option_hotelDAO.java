package be.bentouhami.reservotelapp.Model.DAO.Details_reservation_option_hotelDAO;

public interface IDetails_reservation_option_hotelDAO {

    void getAllDetailsReservationOptionHotel();

    void writeDetailsReservationOptionHotel(int idOptionHotel, int idDetailsReservation);

    boolean close();
}
