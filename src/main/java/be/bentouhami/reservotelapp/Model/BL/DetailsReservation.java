package be.bentouhami.reservotelapp.Model.BL;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DetailsReservation {
    private int idDetailsReservation;
    private int reservation_id;
    private int id_chambre;
    private Chambre chambre;
    private double prixChambre; // prux de cette chambre
    private ArrayList<Option> options;
    private double prix_total_details_reservation; // prix total de la chamrbe + les options

    public DetailsReservation(int idDetailsReservation,
                              int reservation_id,
                              int id_chambre,
                              double prix_total_details_reservation) {
        this.idDetailsReservation = idDetailsReservation;
        this.reservation_id = reservation_id;
        this.id_chambre = id_chambre;
        this.prix_total_details_reservation = prix_total_details_reservation;
    }


    public DetailsReservation(int idDetailsReservation,
                              Chambre chambre,
                              double prixChambre,
                              ArrayList<Option> options_hotel) {
        this.idDetailsReservation = idDetailsReservation;
        this.chambre = chambre;
        this.prixChambre = prixChambre;
        this.options = options_hotel;
    }

    public DetailsReservation() {

    }


    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getId_chambre() {
        return id_chambre;
    }

    public void setId_chambre(int id_chambre) {
        this.id_chambre = id_chambre;
    }

    public void setIdDetailsReservation(int idDetailsReservation) {
        this.idDetailsReservation = idDetailsReservation;
    }

    public int getIdDetailsReservation() {
        return idDetailsReservation;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public double getPrixChambre() {
        return prixChambre;
    }

    public void setPrixChambre(double prixChambre) {
        this.prixChambre = prixChambre;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public double getPrix_total_details_reservation() {
        return prix_total_details_reservation;
    }

    public void setPrix_total_details_reservation(double prix_total_details_reservation) {
        this.prix_total_details_reservation = prix_total_details_reservation;
    }

    public double calculPrixOptions(Double... prixOptions) {
        double totalPrixOption = 0;
        for (Double prixOption : prixOptions) {
            totalPrixOption += prixOption;
        }
        return totalPrixOption;
    }

    /**
     * calculer la réduction sur le prix de la chambre uniquement, prixChambre * pourcentage = prixTotalReduction par jour
     * réduit le prixChambre de 10%. Le facteur 0.9 provient de la soustraction de la réduction (10%) de 1 (ou 100%).
     *
     * @param prixParNuit
     * @param pourcentage
     * @param dateArrive
     * @param dateDepart
     * @return
     */

    public double calculPrixSaison(double prixParNuit,
                                   LocalDate dateArrive,
                                   LocalDate dateDepart,
                                   double pourcentage) {
        double reductionSaison = 0;
        LocalDate currentDate = dateArrive;

        // inclure le jour de départ dans le calcul
        LocalDate endDate = dateDepart;
        // pour inclure le jour de départ dans le calcul
        // commenter la ligne suivante où en retire le dernier jour
        if (!dateArrive.isEqual(endDate)) {
            endDate = dateDepart.minusDays(1); // exclure le jour de départ
            while (!currentDate.isAfter(endDate)) {
                boolean estHorsSaison =
                        (currentDate.getMonthValue() >= 9 ||
                                currentDate.getMonthValue() <= 6);
                if (estHorsSaison) {
                    // Appliquer réduction hors saison
                    reductionSaison += prixParNuit * pourcentage;
                }
                currentDate = currentDate.plusDays(1);
            }
        } else {
            boolean estHorsSaison =
                    (currentDate.getMonthValue() >= 9 ||
                            currentDate.getMonthValue() <= 6);
            if (estHorsSaison) {
                // Appliquer réduction hors saison
                reductionSaison += prixParNuit * pourcentage;
            }
        }
            return reductionSaison;
    }


    public double calculTotalPrixDetailsReservation(double newPrixChambre, double prixTotalOptions) {
        return newPrixChambre + prixTotalOptions;
    }

    /**
     * Calculer les jours de séjour
     *
     * @return le nombre des jours.
     */

    public int calculateDureeSejour(LocalDate dateArrive, LocalDate dateDepart) {
        // retirer 1 pour calculer les nuit
        if(dateArrive.isEqual(dateDepart)){
            return 1;
        }
        return (int) ChronoUnit.DAYS.between(dateArrive, dateDepart);
    }

    public double calculPrixTotalChambre(double prixChambre, int dureeSejour, double reductionSaison,
                                         double prixOptions) {

        return ((prixChambre * dureeSejour) - reductionSaison) + prixOptions;

    }

    /**
     * @param nombreDeVoyageurs
     * @return
     */
    public int calculNombrePersonnesRestantes(int nombreDeVoyageurs) {
        return nombreDeVoyageurs - this.getChambre().getNombre_personnes();
    }
}
