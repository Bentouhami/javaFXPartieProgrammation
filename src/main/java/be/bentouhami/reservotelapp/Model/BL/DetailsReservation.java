package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;

public class DetailsReservation {
    private int idDetailsReservation;
    private int reservation_id;
    private int id_chambre;
    private Chambre chambre;
    private double prixChambre; // prux de cette chambre
    private ArrayList<Option> options;
    private double prix_total_details_reservation; // prix total de la chamrbe + les options

    public DetailsReservation(int idDetailsReservation,
                              int reservation_id,
                              int id_chambre,
                              double prix_total_details_reservation) {
        this.idDetailsReservation = idDetailsReservation;
        this.reservation_id = reservation_id;
        this.id_chambre = id_chambre;
        this.prix_total_details_reservation = prix_total_details_reservation;
    }

    public DetailsReservation(Chambre chambre,
                              double prixChambre,
                              ArrayList<Option> options,
                              double prix_total_details_reservation) {
        this.chambre = chambre;
        this.prixChambre = prixChambre;
        this.options = options;
        this.prix_total_details_reservation = prix_total_details_reservation;
    }


    public DetailsReservation(int idDetailsReservation,
                              Chambre chambre,
                              double prixChambre,
                              ArrayList<Option> options_hotel,
                              double prix_total_details_reservation) {
        this.idDetailsReservation = idDetailsReservation;
        this.chambre = chambre;
        this.prixChambre = prixChambre;
        this.options = options_hotel;
        this.prix_total_details_reservation = prix_total_details_reservation;
    }

    public DetailsReservation() {

    }

    private double calculPrixOptions(ArrayList<Option_hotel> options_hotel) {

        double prixOptions = 0;
        for (Option_hotel option_hotel : options_hotel) {
            // récupérer le prix de chaque option par l'id d'hotel et l'id option
            double prixOption = option_hotel.getPrix_option();
            prixOptions += prixOption;
        }
        return prixOptions;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getId_chambre() {
        return id_chambre;
    }

    public void setId_chambre(int id_chambre) {
        this.id_chambre = id_chambre;
    }

    public void setIdDetailsReservation(int idDetailsReservation) {
        this.idDetailsReservation = idDetailsReservation;
    }

    public int getIdDetailsReservation() {
        return idDetailsReservation;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public double getPrixChambre() {
        return prixChambre;
    }

    public void setPrixChambre(double prixChambre) {
        this.prixChambre = prixChambre;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public double getPrix_total_details_reservation() {
        return prix_total_details_reservation;
    }

    public void setPrix_total_details_reservation(double prix_total_details_reservation) {
        this.prix_total_details_reservation = prix_total_details_reservation;
    }
}
