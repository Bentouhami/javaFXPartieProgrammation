package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.Client;
import be.bentouhami.reservotelapp.Model.BL.ClientConnecte;
import be.bentouhami.reservotelapp.Model.BL.HotelList;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.AdresseDAO;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.IAdressesDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.ClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.IClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.HotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.IHotelDAO;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

public class Model implements IModel {
    private IClientDAO clientDAO;
    private ClientConnecte clientConnecte;
    private HotelList hotelsList;
    private PropertyChangeSupport support;
    private IHotelDAO iHotelDAO;
    private IAdressesDAO adressesDAO;


    public Model() throws SQLException {
        this.support = new PropertyChangeSupport(this);
        this.iHotelDAO = new HotelDAO();

    }


    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public void showHotels(String ville, String dateArrive, String dateDepart, String nbrPersonne) {
        hotelsList = iHotelDAO.getHotels(ville);
        support.firePropertyChange("hotelsList", "", this.hotelsList);
    }

    @Override
    public void logout() {

    }

    @Override
    public void showProfil() {

    }

    @Override
    public void checkLogin(String email, String password){

        Client client = null;
        // ClientConnecte clientConnecte = null;
        try {
            client = this.clientDAO.getClientByEmail(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(client != null){


        }

    }



    // WORKS
    @Override
    public boolean addClient(int idAdresse,
                                String nom,
                                String prenom,
                                String dateNaissance,
                                String numTel,
                                String email,
                                String password) {
        try {
            this.clientDAO = new ClientDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  this.clientDAO.addClient(idAdresse,
                nom,
                prenom,
                dateNaissance,
                numTel,
                email,
                0,
                password);

    }

    // WORKS
    @Override
    public int addAdresse(String rue,
                              String numRue,
                              String boite,
                              String codePostal,
                              String ville,
                              String pays) {
        try {
            adressesDAO = new AdresseDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int id = adressesDAO.addAdresse(rue,
        numRue,
        boite,
        codePostal,
        ville,
        pays);

        return id;

    }


    @Override
    public void close() {
        this.iHotelDAO.close();
    }
}
