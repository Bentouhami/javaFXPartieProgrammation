package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.Adresse;
import be.bentouhami.reservotelapp.Model.BL.Client;

import java.beans.PropertyChangeListener;

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

    void showHotels(String ville,
                    String dateArrive,
                    String dateDepart,
                    String nbrPersonne);

    void logout();

    void showProfil();

    Client checkLogin(String email, String password);

    boolean addClient(int idAdresse,
                      String nom,
                      String prenom,
                      String dateNaissance,
                      String numTel,
                      String email,
                      String password);

    Adresse getAdresseByID(int idClient);
}
