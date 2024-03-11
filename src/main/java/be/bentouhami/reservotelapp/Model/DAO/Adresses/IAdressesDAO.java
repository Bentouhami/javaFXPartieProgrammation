package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.Model.BL.Adresse;

import java.util.ArrayList;
import java.util.List;

public interface IAdressesDAO {
    boolean updateAdresse_dao(ArrayList<String> adresseNewValues);

    int addAdresse_dao(String rue,
                       String numRue,
                       String boite,
                       String codePostal,
                       String ville,
                       String pays);

    Adresse getAdresseByID(int id);

    ArrayList<String> getAllPays();

    ArrayList<String> getAllVilles(String pays);

    //boolean close();

    void insert(Adresse adresse);

    List<Integer> getAdressesId();

    List<Adresse> getadresses();
}
