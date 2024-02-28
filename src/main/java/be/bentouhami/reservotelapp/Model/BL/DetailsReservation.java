package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;

public class DetailsReservation {
    private int idDetailsReservation;
    private Chambre chambre;
    private double prixChambre;
    private ArrayList<Option> options;
    private double prixTotal;

    public DetailsReservation(int idDetailsReservation,
                              Chambre chambre,
                              double prixChambre,
                              ArrayList<Option> options,
                              double prixTotal) {
        this.idDetailsReservation = idDetailsReservation;
        this.chambre = chambre;
        this.prixChambre = prixChambre;
        this.options = options;
        this.prixTotal = prixTotal;
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
