package be.bentouhami.reservotelapp.Model.BL;

public class Chambre {
    private long idChambret;
    private String numeroChanbe;
    private int etage;
    private int nombrePersonnesMax;
    private boolean estDisponible;
    private String photo;
    private double prixBase;

    public Chambre(long idChambret,
                   String numeroChanbe,
                   int etage,
                   int nombrePersonnesMax,
                   boolean estDisponible,
                   String photo,
                   double prixBase) {
        this.idChambret = idChambret;
        this.numeroChanbe = numeroChanbe;
        this.etage = etage;
        this.nombrePersonnesMax = nombrePersonnesMax;
        this.estDisponible = estDisponible;
        this.photo = photo;
        this.prixBase = prixBase;
    }// end constructor

    public long getIdChambret() {
        return idChambret;
    }

    public String getNumeroChanbe() {
        return numeroChanbe;
    }

    public void setNumeroChanbe(String numeroChanbe) {
        this.numeroChanbe = numeroChanbe;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public int getNombrePersonnesMax() {
        return nombrePersonnesMax;
    }

    public void setNombrePersonnesMax(int nombrePersonnesMax) {
        this.nombrePersonnesMax = nombrePersonnesMax;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
    }
}
