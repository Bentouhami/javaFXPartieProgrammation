package be.bentouhami.reservotelapp.Model.BL;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Date;

public class Client {
    private int idClient;

    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private String numeroTelephone;
    private int pointsFidelite;
    private int idAdresse;
    private String password;

    public Client() {
    }

    public Client(int idClient,
                  int adresse,
                  String nom,
                  String prenom,
                  Date dateNaissance,
                  String email,
                  String numeroTelephone,
                  int pointsFidelite,
                  String password)
                  {
                        this.idClient = idClient;
                        this.nom = nom;
                        this.prenom = prenom;
                        this.dateNaissance = dateNaissance;
                        this.email = email;
                        this.numeroTelephone = numeroTelephone;
                        this.pointsFidelite = pointsFidelite;
                        this.idAdresse = adresse;
                        setPassword(password);
                  }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getIdClient() {
        return idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public int getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(int pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    private String hashMotDePasse(String password){

            return BCrypt.hashpw(password, BCrypt.gensalt());

    }


    //region Methods
//    private String hashMotDePasse(String password){
//        try {
//            // Créer l'instance de MessageDigest pour SHA-256
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//            // Hacher le mot de passe
//            byte[] encodedHash = digest.digest(password.getBytes());
//
//            // Convertir le hachage en chaîne hexadécimale
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : encodedHash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public boolean checkNom(String nom){
        if (nom.isEmpty()) System.out.println();

       return (!nom.isEmpty()) && nom.matches("[A-Z][a-z]*([-'][A-Za-z]+)*\\s?([A-Z][a-z]*([-'][A-Za-z]+)*)*");

    }

    //endregion
}
