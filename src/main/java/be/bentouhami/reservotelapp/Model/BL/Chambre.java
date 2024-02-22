package be.bentouhami.reservotelapp.Model.BL;

public class Chambre {
    private int id_chambre;
    private int hotel_id;


    private String numero_chambre;
    private int etage;
    private int nombre_personnes;
    private boolean est_disponible;
    private String photo_chambre;
    private String type_chambre;
    private String lits;
    private double prix_chambre;

    public Chambre() {
    }

    public Chambre(int id_chambre,
                   int hotel_id,
                   String numero_chambre,
                   int etage,
                   int nombre_personnes,
                   boolean est_disponible,
                   String photo_chambre,
                   String type_chambre,
                   String lits,
                   double prix_chambre) {
        this.id_chambre = id_chambre;
        this.hotel_id = hotel_id;
        this.numero_chambre = numero_chambre;
        this.etage = etage;
        this.nombre_personnes = nombre_personnes;
        this.est_disponible = est_disponible;
        this.photo_chambre = photo_chambre;
        this.type_chambre = type_chambre;
        this.lits = lits;
        this.prix_chambre = prix_chambre;
    }

    public int getId_chambre() {
        return id_chambre;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getNumero_chambre() {
        return numero_chambre;
    }

    public void setNumero_chambre(String numero_chambre) {
        this.numero_chambre = numero_chambre;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public int getNombre_personnes() {
        return nombre_personnes;
    }

    public void setNombre_personnes(int nombre_personnes) {
        this.nombre_personnes = nombre_personnes;
    }

    public boolean isEst_disponible() {
        return est_disponible;
    }

    public void setEst_disponible(boolean est_disponible) {
        this.est_disponible = est_disponible;
    }

    public String getPhoto_chambre() {
        return photo_chambre;
    }

    public void setPhoto_chambre(String photo_chambre) {
        this.photo_chambre = photo_chambre;
    }

    public String getType_chambre() {
        return type_chambre;
    }

    public void setType_chambre(String type_chambre) {
        this.type_chambre = type_chambre;
    }

    public String getLits() {
        return lits;
    }

    public void setLits(String lits) {
        this.lits = lits;
    }

    public double getPrix_chambre() {
        return prix_chambre;
    }

    public void setPrix_chambre(double prix_chambre) {
        this.prix_chambre = prix_chambre;
    }

}
