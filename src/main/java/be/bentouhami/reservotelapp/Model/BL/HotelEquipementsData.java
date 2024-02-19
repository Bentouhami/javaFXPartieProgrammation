package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;

public class HotelEquipementsData {
    private ArrayList<Equipement> equipements;
    private Hotel hotel;
    public HotelEquipementsData(Hotel hotel, ArrayList<Equipement> equipements) {
        this.hotel = hotel;
        this.equipements = equipements;
    }

    // Getters
    public ArrayList<Equipement> getEquipements() {
        return equipements;
    }

    public Hotel getHotel() {
        return hotel;
    }
}

