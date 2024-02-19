package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.Adresse;
import be.bentouhami.reservotelapp.Model.BL.Client;
import be.bentouhami.reservotelapp.Model.BL.Equipement;
import be.bentouhami.reservotelapp.Model.BL.Hotel;

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

    boolean verifyPassword(int clientId, String oldPassword);

    void getHotels(String ville,
                   String dateArrive,
                   String dateDepart,
                   String nbrPersonne);

    Client getClientByEmail(String email);
    boolean validateLogin(String email, String password);


    Client getClientByID(int id);

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

    ArrayList<String> getAllEquipements();

    ArrayList<String> getAllPrix();
    ArrayList<Equipement> getHotelEquipements(Hotel hotelId);

    Equipement getEquipementByHotelId(String hotelId);

    Hotel getHotelById(String hotelId);

    void getOptions(String idHotel);
}
