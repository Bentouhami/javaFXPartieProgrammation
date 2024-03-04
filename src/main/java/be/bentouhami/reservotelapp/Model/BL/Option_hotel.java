package be.bentouhami.reservotelapp.Model.BL;

public class Option_hotel {
    int hotel_id;
    int option_id;
    private double prix_option;

    public Option_hotel(int hotel_id,
                        int option_id,
                        double prix_option) {
        this.hotel_id = hotel_id;
        this.option_id = option_id;
        this.prix_option = prix_option;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public int getOption_id() {
        return option_id;
    }

    public double getPrix_option() {
        return prix_option;
    }

    public void setPrix_option(double prix_option) {
        this.prix_option = prix_option;
    }
}
