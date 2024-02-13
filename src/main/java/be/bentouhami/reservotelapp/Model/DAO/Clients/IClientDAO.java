package be.bentouhami.reservotelapp.Model.DAO.Clients;

import be.bentouhami.reservotelapp.Model.BL.Client;

import java.util.ArrayList;

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
    //boolean updateClient();

    boolean getValidation(String email, String password)// end methode
    ;

    Client getClientByID(int id);


    boolean updateClient(ArrayList<String> clientNewValues);

    boolean updateCLientAdresse(int id_client, int adresse_id);

    boolean verifyClientPassword(int clientId, String oldPassword);
}
