package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private int idReservation;
    private Hotel hotel;
    private Date dateArrive;
    private Date dateDepart;
    private double prixTotal;
    private int nombrePersonnes;
    private String ville;
    private ArrayList<DetailsReservation> detailsReservation ;

    public Reservation() {
    }

    public Reservation(int idReservation,
                       Hotel hotel,
                       Date dateArrive,
                       Date dateDepart,
                       double prixTotal, // Le prix total de la réservation. Va additionner toutes les chambres.
                       int nombrePersonnes,
                       String ville,
                       ArrayList<DetailsReservation> detailsReservation) {
        this.idReservation = idReservation;
        this.hotel = hotel;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.prixTotal = prixTotal;
        this.nombrePersonnes = nombrePersonnes;
        this.ville = ville;
        this.detailsReservation = detailsReservation;
    }// end constructor

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public ArrayList<DetailsReservation> getDetailsReservation() {
        return detailsReservation;
    }

    public void setDetailsReservation(ArrayList<DetailsReservation> detailsReservation) {
        this.detailsReservation = detailsReservation;
    }


}
