package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;

public class Hotel {
    private long idHotel;
    private String nom;
    private int idAdresse;
    private int etoils;
    private String descrition;
    private String photo;
    private  double prixChambreMin;
    private int nombreChambres;
    private String contactTelephone;
    private String contactEmail;
    private ArrayList<Equipement> equipements;


    public Hotel (){

    }
    public Hotel(long idHotel,
                 int adresse,
                 String nom,
                 int etoils,
                 String descrition,
                 double prixChambreMin,
                 int nombreChambres,
                 String contactTelephone,
                 String photo,
                 String contactEmail
                 ) {
        this.idHotel = idHotel;
        this.nom = nom;
        this.idAdresse = adresse;
        this.etoils = etoils;
        this.descrition = descrition;
        this.photo = photo;
        this.prixChambreMin = prixChambreMin;
        this.nombreChambres = nombreChambres;
        this.contactTelephone = contactTelephone;
        this.contactEmail = contactEmail;

    }

    public long getIdHotel() {
        return idHotel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    public int getEtoils() {
        return etoils;
    }

    public void setEtoils(int etoils) {
        this.etoils = etoils;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrixChambreMin() {
        return prixChambreMin;
    }

    public void setPrixChambreMin(double prixChambreMin) {
        this.prixChambreMin = prixChambreMin;
    }

    public int getNombreChambres() {
        return nombreChambres;
    }

    public void setNombreChambres(int nombreChambres) {
        this.nombreChambres = nombreChambres;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public ArrayList<Equipement> getEquipements() {
        return equipements;
    }

    public void setEquipements(ArrayList<Equipement> equipements) {
        this.equipements = equipements;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nom='" + nom + '\'' +
                ", descrition='" + descrition + '\'' +
                ", photo='" + photo + '\'' +
                ", nombreChambres=" + nombreChambres +
                ", contactTelephone='" + contactTelephone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
