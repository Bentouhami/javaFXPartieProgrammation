package be.bentouhami.reservotelapp.Model.BL;

public class Adresse {
    private int idAdresse;
    private String rue;
    private String numero;
    private String boite;
    private String ville;
    private String codePostal;
    private String pays;


    public Adresse(int idAdresse,
                   String rue,
                   String numero,
                   String boite,
                   String ville,
                   String codePostal,
                   String pays) {

        this.idAdresse = idAdresse;
        this.rue = rue;
        this.numero = numero;
        this.boite = boite;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
    }// end constructor
    public Adresse(int idAdresse,
                   String ville,
                   String codePostal,
                   String pays) {

        this.idAdresse = idAdresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
    }// end constructor

    public Adresse(String rue,
                   String numero,
                   String boite,
                   String ville,
                   String codePostal,
                   String pays) {
        this.rue = rue;
        this.numero = numero;
        this.boite = boite;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;

    }

    public Adresse(int idAdresse, String ville, String pays) {
        this.idAdresse = idAdresse;
        this.ville = ville;
        this.pays = pays;


    }


    public int getIdAdresse() {
        return idAdresse;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBoite() {
        return boite;
    }

    public void setBoite(String boite) {
        this.boite = boite;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
