package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface IModel {
    int addAdresse(String rue,
                       String numRue,
                       String boite,
                       String codePostal,
                       String ville,
                       String pays);

    public void close();

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

    ArrayList<String> getAllPays();

    ArrayList<String> getAllVillesByPays(String pays);

    ArrayList<Equipement> getHotelEquipements(Hotel hotelId);

    ArrayList<String[]> getOptions(String idHotel);

    boolean validatePhone(int idClient, String email, String numeroTelephone);

    boolean updatePassword(int idClient, String newPassword);

    void getChambreDatas(String idClient, String hotelId, String idChambre, ArrayList<String[]> options);

    Chambre getChambreByIdAndHotelId(String id_chambre, String id_hotel);

    Reservation writeReservationAndDetailsReservation(String idClient, String[] hotelSearchDatas, ArrayList<String> selectedChambre, ArrayList<String[]> selectedOptions);

    Hotel getHotelById(String hotel_id);

    ArrayList<String[]> getAllReservations(String client_id);

    ReservationList getReservation();
}
