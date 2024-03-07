package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private int idReservation;
    private int clientId;
    private Hotel hotel;
    private Date dateArrive;
    private Date dateDepart;
    private double prixTotal;
    private int nombrePersonnes;
    private String ville;
    private ArrayList<DetailsReservation> detailsReservationList;
    public Reservation() {

    }

    public Reservation(int idReservation,
                       int client_id,
                       Hotel hotel,
                       Date dateArrive,
                       Date dateDepart,
                       double prixTotal, // Le prix total de la réservation. Va additionner toutes les chambres.
                       int nombrePersonnes,
                       String ville,
                       ArrayList<DetailsReservation> detailsReservationList) {
        this.idReservation = idReservation;
        this.clientId = client_id;
        this.hotel = hotel;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.prixTotal = prixTotal;
        this.nombrePersonnes = nombrePersonnes;
        this.ville = ville;
        this.detailsReservationList = detailsReservationList;
    }// end constructor

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
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

    public ArrayList<DetailsReservation> getDetailsReservationList() {
        return detailsReservationList;
    }

    public void setDetailsReservationList(ArrayList<DetailsReservation> detailsReservationList) {
        this.detailsReservationList = detailsReservationList;
    }

}
