package be.bentouhami.reservotelapp.Model.BL;

public class Option {
    private long idOption;
    private String nomOption;
    private double prixOption;

    public Option(long idOption,
                  String nomOption,
                  double prixOption) {
        this.idOption = idOption;
        this.nomOption = nomOption;
        this.prixOption = prixOption;
    }// end Constructor


    public long getIdOption() {
        return idOption;
    }

    public String getNomOption() {
        return nomOption;
    }

    public void setNomOption(String nomOption) {
        this.nomOption = nomOption;
    }

    public double getPrixOption() {
        return prixOption;
    }

    public void setPrixOption(double prixOption) {
        this.prixOption = prixOption;
    }
}


