package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.Model.BL.Adresse;

import java.util.ArrayList;

public interface IAdressesDAO {
    boolean updateAdresse_dao(ArrayList<String> adresseNewValues);
    boolean close_dao();


    int addAdresse_dao(String rue,
                       String numRue,
                       String boite,
                       String codePostal,
                       String ville,
                       String pays);

    Adresse getAdresseByID_dao(int id);

    ArrayList<String> getAllPays();

    ArrayList<String> getAllVilles(String pays);
}
