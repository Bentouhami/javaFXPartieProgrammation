package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.*;
import be.bentouhami.reservotelapp.Model.BL.Containers.ChambreDatas;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface IModel {
    int addAdresse(String rue,
                   String numRue,
                   String boite,
                   String codePostal,
                   String ville,
                   String pays);

    void close();

    void addPropertyChangeListener(PropertyChangeListener pcl);

    void removePropertyChangeListener(PropertyChangeListener pcl);

    void getHotels(String ville,
                   String dateArrive,
                   String dateDepart,
                   String nbrPersonne);

    Client getClientByEmail(String email);

    boolean validateLogin(String email, String password);

    boolean addClient(int idAdresse,
                      String nom,
                      String prenom,
                      String dateNaissance,
                      String numTel,
                      String email,
                      String password);

    Adresse getAdresseByID_model(int idClient);

    void updateClientConnected(ArrayList<String> clientNewValues);

    void getChambresByHotelId(String idHotel);

    Chambre getChambreByIdAndHotelId(String idChambre, String idHotel);

    ArrayList<String> getAllPays();

    ArrayList<String> getAllVillesByPays(String pays);

    ArrayList<Equipement> getHotelEquipements(Hotel hotelId);

    ArrayList<String[]> getOptions(String idHotel);

    boolean validatePhone(int idClient, String email, String numeroTelephone);

    boolean updatePassword(int idClient, String newPassword);

    void getChambreDatas(String idClient, String hotelId, String idChambre, ArrayList<String[]> options);

    void writeReservationAndDetailsReservation(String[] hotelSearchDatas, ChambreDatas selectedChambre, ArrayList<String[]> selectedOptions);

    Hotel getHotelById(String hotel_id);

    void getAllReservations(String client_id);

    void showRecapReservation();

    boolean finalizeReservation();

    void prepareNewReservation();


    int verifierNombresPersonnesRestantes();

    double calculReductionFidelite(int points, int idClient, int client);

    void calculAjoutPointsFidelite(int points, int idReservation, int idClient);

    void calculTotalReservation(double points, int idReservation, int idClient);
}