package be.bentouhami.reservotelapp.Model.DAO.Adresses;

import be.bentouhami.reservotelapp.Model.BL.Adresse;

public interface IAdressesDAO {
    boolean getAdresse();

    boolean editAdresse();
    boolean close();


    int addAdresse(String rue,
                       String numRue,
                       String boite,
                       String codePostal,
                       String ville,
                       String pays);

    Adresse getAdresseByID(int id);
}
