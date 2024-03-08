package be.bentouhami.reservotelapp.Model.BL.Containers;

import java.util.ArrayList;

public class ReservationContainer {
    private ArrayList<String[]> reservationsList;
    private ArrayList<String[]> detailsReservationList;
    private ArrayList<String[]> chambresList;

    public ReservationContainer() {
    }

    public ReservationContainer(ArrayList<String[]> reservationsList,
                                ArrayList<String[]> detailsReservationList,
                                ArrayList<String[]> chambresList) {
        this.reservationsList = reservationsList;
        this.detailsReservationList = detailsReservationList;
        this.chambresList = chambresList;
    }


    public ArrayList<String[]> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(ArrayList<String[]> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public ArrayList<String[]> getDetailsReservationList() {
        return detailsReservationList;
    }

    public void setDetailsReservationList(ArrayList<String[]> detailsReservationList) {
        this.detailsReservationList = detailsReservationList;
    }

    public ArrayList<String[]> getChambresList() {
        return chambresList;
    }

    public void setChambresList(ArrayList<String[]> chambresList) {
        this.chambresList = chambresList;
    }
}
