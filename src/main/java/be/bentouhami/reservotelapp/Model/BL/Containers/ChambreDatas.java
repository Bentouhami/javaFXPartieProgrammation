package be.bentouhami.reservotelapp.Model.BL.Containers;

import java.util.ArrayList;

public class ChambreDatas {


    private String idClient;
    private String idChambre;
    private String idHotel;
    private ArrayList<String> chambreDetails;
    private ArrayList<String[]> options;

    public ChambreDatas() {
    }

    public ChambreDatas(String idClient,
                        String idHotel,
                        String idChambre,
                        ArrayList<String> chambreDetails) {
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.idHotel = idHotel;
        this.chambreDetails = chambreDetails;
        this.options = new ArrayList<String[]>();
    }

    public void addOption(ArrayList<String[]> option) {
        options.addAll(option);
    }

    public String getIdClient() {
        return idClient;
    }
    // Getters
    public String getIdChambre() {
        return idChambre;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public ArrayList<String> getChambreDetails() {
        return chambreDetails;
    }

    public ArrayList<String[]> getOptions() {
        return options;
    }
}

