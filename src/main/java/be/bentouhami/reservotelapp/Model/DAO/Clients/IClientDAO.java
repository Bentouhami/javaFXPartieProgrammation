package be.bentouhami.reservotelapp.Model.DAO.Clients;

import be.bentouhami.reservotelapp.Model.BL.Client;

public interface IClientDAO {

    boolean addClient (int idAdresse,
                          String nom,
                          String prenom,
                          String dateNaissance,
                          String numTel,
                          String email,
                          int points_fidelite,
                          String password);

    Client getClientByEmail(String identifiantEmail);

    Client getClientByID(int id);


}
