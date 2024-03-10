package be.bentouhami.reservotelapp.Model.BL;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reservation {
    private final int MAXIMUM_POINTS = 10_000;
    private final double TAUX_REDUCTION = 0.01;
    private Timestamp date_creation;
    private String statut_reservation;
    private int idReservation;
    private int clientId;
    private Hotel hotel;
    private LocalDate dateArrive;
    private LocalDate dateDepart;
    private double prixTotal;
    private int nombrePersonnes;
    private String ville;
    private ArrayList<DetailsReservation> detailsReservationList;

    public Reservation() {

    }

    public Reservation(int idReservation,
                       int clientId,
                       String statut_reservation,
                       LocalDate dateArrive,
                       LocalDate dateDepart,
                       double prixTotal,
                       Timestamp date_creation) {
        this.idReservation = idReservation;
        this.clientId = clientId;
        this.statut_reservation = statut_reservation;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.prixTotal = prixTotal;
        this.date_creation = date_creation;
    }

    public Reservation(int idReservation,
                       int client_id,
                       Hotel hotel,
                       LocalDate dateArrive,
                       LocalDate dateDepart,
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

    public String getStatut_reservation() {
        return statut_reservation;
    }


    public Timestamp getDate_creation() {
        return date_creation;
    }

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

    public LocalDate getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(LocalDate dateArrive) {
        this.dateArrive = dateArrive;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
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

    /**
     * calcule le nombre total de personnes restantes qui ne peuvent pas être hébergées
     * par les chambres actuellement réservées.
     *
     * @return le nombre de personnes restantes positif (reste encore des clients sans lit)
     * ou négatif (le client a reservé plus qu'il a besoin)
     */
    public int calculerPersonnesRestantes() {

        if (detailsReservationList != null && nombrePersonnes > 2) {
            int capaciteTotale = 0;

            for (DetailsReservation details : detailsReservationList) {
                // récupérer et addition le nombre maximum pour chaque chambre
                capaciteTotale += details.getChambre().getNombre_personnes();
            }

            int deficitPersonnes = nombrePersonnes - capaciteTotale; // calculer le nombre de personnes non hébergées
            if (deficitPersonnes <= 0) {
                return 0; // toutes les personnes peuvent être hébergées avec les chambres actuelles
            }

            // calcule le nombre de chambres supplémentaires de deux personnes nécessaires
            // soustraire le nombre total maximum pour toutes les chambres de nombre des voyageurs pour cette reservation
            // retourne le nombre de personnes restantes positif (reste encore des clients sans lit)
            // ou négatif = le client a reservé plus qu'il a besoin
            return (int) Math.ceil((double) deficitPersonnes / 2);
        }
        return 0;
    }


    public double calculTotalReservation(double reductionFidelite, double prixTotalReservation) {
        //TODO Cette méthode calcule le prix total de la réservation.
        // Elle prend donc le prix total de toutes les chambres et applique la réduction des points de fidélité.
            return prixTotalReservation - reductionFidelite;
    }

    /**
     * Cette fonction va calculer la réduction que le client recevra s'il souhaite dépenser ses points de fidélité.
     * Si le client veut utiliser ses points, il est obligé de
     * dépenser la totalité des points. Chaque point vaut 0.01€.
     *
     * @param pointsFelite
     * @return
     */
    public double calculReductionFidelite(int pointsFelite) {
        double reductionFidelite = 0;
        if (pointsFelite > 0 && pointsFelite <= MAXIMUM_POINTS) {
            reductionFidelite = pointsFelite * TAUX_REDUCTION;
        }
        return reductionFidelite;
    }

    /**
     * A chaque réservation, le client reçoit des points de fidélité qui varient en fonction du coût de la réservation.
     * Le client reçoit 1 point par 1€ dépensé. Le client
     * peut avoir maximum 10.000 points.
     *
     * @return
     */
    public int calculAjoutPointsFidelite(int pointsActuels, double prixTotalReservation) {
        int pointsAAjouter = (int)prixTotalReservation; // 1 point par euro dépensé
        int nouveauTotalPoints = pointsActuels + pointsAAjouter;
        return Math.min(nouveauTotalPoints, MAXIMUM_POINTS); // Ne pas dépasser 10 000 points
    }



}
