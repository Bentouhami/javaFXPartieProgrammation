package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;

public class DetailsReservation {
    private int idDetailsReservation;
    private Chambre chambre;
    private double prixChambre; // prux de cette chambre
    private ArrayList<Option> options;
    private double prixTotal; // prix total de la chamrbe + les options

    public DetailsReservation(Chambre chambre,
                              double prixChambre,
                              ArrayList<Option> options,
                              double prixTotal) {
        this.chambre = chambre;
        this.prixChambre = prixChambre;
        this.options = options;
        this.prixTotal = prixTotal;
    }

    public DetailsReservation(int idDetailsReservation,
                              Chambre chambre,
                              double prixChambre,
                              ArrayList<Option> options_hotel,
                              double prixTotal) {
        this.idDetailsReservation = idDetailsReservation;
        this.chambre = chambre;
        this.prixChambre = prixChambre;
        this.options = options_hotel;
        this.prixTotal = prixTotal;
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

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
