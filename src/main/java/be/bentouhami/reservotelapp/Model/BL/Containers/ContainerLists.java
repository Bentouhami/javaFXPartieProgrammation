package be.bentouhami.reservotelapp.Model.BL.Containers;

import be.bentouhami.reservotelapp.Model.BL.*;

import java.util.ArrayList;

public class ContainerLists {
    public ChambreList getChambres() {
        return chambres;
    }

    public void setChambres(ChambreList chambres) {
        this.chambres = chambres;
    }

    private ChambreList chambres;
    private String ville;
    private HotelList hotels;
    private Hotel hotel;
    private ArrayList<Equipement> equipementsList;
    private OptionList options;
    private String dateArriver;
    private String dateDepart;
    private String nbrPersonne;

    public ContainerLists(HotelList hotels,
                          String ville,
                          String dateArriver,
                          String dateDepart,
                          String nbrPersonne) {
        this.hotels = hotels;
        this.ville = ville;
        this.dateArriver = dateArriver;
        this.dateDepart = dateDepart;
        this.nbrPersonne = nbrPersonne;
    }

    public ContainerLists (Hotel hotel, OptionList options){
        this.hotel = hotel;
        this.options = options;
    }

    public  ContainerLists(Hotel hotel, ChambreList chambres){
        this.hotel = hotel;
        this.chambres = chambres;
    }
    public  ContainerLists(Hotel hotel, ArrayList<Equipement> equipementsList){
        this.hotel = hotel;
        this.equipementsList = equipementsList;
    }

    public ArrayList<Equipement> getEquipementsList() {
        return equipementsList;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public OptionList getOptions() {
        return options;
    }

    public void setOptions(OptionList options) {
        this.options = options;
    }



    public HotelList getHotels() {
        return hotels;
    }

    public void setHotels(HotelList hotels) {
        this.hotels = hotels;
    }

    public String getDateArriver() {
        return dateArriver;
    }

    public void setDateArriver(String dateArriver) {
        this.dateArriver = dateArriver;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getNbrPersonne() {
        return nbrPersonne;
    }

    public void setNbrPersonne(String nbrPersonne) {
        this.nbrPersonne = nbrPersonne;
    }
    public String getVille() {
        return ville;
    }


    public void setVille(String ville) {
        this.ville = ville;
    }
}
