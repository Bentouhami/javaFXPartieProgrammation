package be.bentouhami.reservotelapp.Model.BL;

import java.util.ArrayList;

public class Hotel {
    private int idHotel;
    private String nom;
    private int adresse_id;
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
    public Hotel(int id_hotel,
                 int adresse_id,
                 String nom_hotel,
                 int etoiles,
                 String description_hotel,
                 String photo_hotel,
                 double prix_chambre_min,
                 int nombre_chambre,
                 String contact_telephone,
                 String contact_email
                 ) {
        this.idHotel = id_hotel;
        this.nom = nom_hotel;
        this.adresse_id = adresse_id;
        this.etoils = etoiles;
        this.descrition = description_hotel;
        this.photo = photo_hotel;
        this.prixChambreMin = prix_chambre_min;
        this.nombreChambres = nombre_chambre;
        this.contactTelephone = contact_telephone;
        this.contactEmail = contact_email;

    }
    public Hotel(int adresse_id,
                 String nom_hotel,
                 int etoiles,
                 String description_hotel,
                 String photo_hotel,
                 double prix_chambre_min,
                 int nombre_chambre,
                 String contact_telephone,
                 String contact_email
                 ) {
        this.adresse_id = adresse_id;
        this.nom = nom_hotel;
        this.etoils = etoiles;
        this.descrition = description_hotel;
        this.photo = photo_hotel;
        this.prixChambreMin = prix_chambre_min;
        this.nombreChambres = nombre_chambre;
        this.contactTelephone = contact_telephone;
        this.contactEmail = contact_email;

    }

    public Hotel(int idHotel, int etoiles) {
        this.idHotel = idHotel;
        this.etoils = etoiles;

    }

    public int getIdHotel() {
        return idHotel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAdresse_id() {
        return adresse_id;
    }

    public void setAdresse_id(int adresse_id) {
        this.adresse_id = adresse_id;
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
