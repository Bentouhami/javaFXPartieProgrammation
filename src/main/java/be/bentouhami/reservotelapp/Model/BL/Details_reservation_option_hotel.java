package be.bentouhami.reservotelapp.Model.BL;

public class Details_reservation_option_hotel {
    private int id_option_hotel;
    private int details_reservation_id;

    public Details_reservation_option_hotel(int id_option_hotel, int details_reservation_id) {
        this.id_option_hotel = id_option_hotel;
        this.details_reservation_id = details_reservation_id;
    }

    public Details_reservation_option_hotel() {
    }

    public int getId_option_hotel() {
        return id_option_hotel;
    }

    public void setId_option_hotel(int id_option_hotel) {
        this.id_option_hotel = id_option_hotel;
    }

    public int getDetails_reservation_id() {
        return details_reservation_id;
    }

    public void setDetails_reservation_id(int details_reservation_id) {
        this.details_reservation_id = details_reservation_id;
    }
}
