package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.*;
import be.bentouhami.reservotelapp.Model.BL.Containers.ContainerLists;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.AdresseDAO;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.IAdressesDAO;
import be.bentouhami.reservotelapp.Model.DAO.Chambres.ChambreDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.ClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.IClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.EquipementDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.HotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.IHotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Options.OptionDAO;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model implements IModel {
    private final OptionDAO optionDAO;
    private final PropertyChangeSupport support;
    private final IHotelDAO hotelDAO;
    private final IClientDAO clientDAO;
    private final IAdressesDAO adressesDAO;
    private HotelList hotelsList;
    private final OptionList options;
    private ChambreList chambresList;
    private final ChambreDAO chambreDAO;
    private final EquipementDAO equipementDAO;

    public Model() throws SQLException {
        this.equipementDAO = new EquipementDAO();
        this.support = new PropertyChangeSupport(this);
        this.hotelDAO = new HotelDAO();
        this.clientDAO = new ClientDAO();
        this.adressesDAO = new AdresseDAO();
        this.hotelsList = new HotelList();
        this.chambreDAO = new ChambreDAO();
        this.chambresList = new ChambreList();
        this.optionDAO = new OptionDAO();
        this.options = new OptionList();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public boolean verifyPassword(int clientId, String oldPassword) {
        return this.clientDAO.verifyClientPassword(clientId, oldPassword);
    }

    @Override
    public void getHotels(String ville, String dateArrive, String dateDepart, String nbrPersonne) {

        hotelsList = this.hotelDAO.getHotels(ville);
        ContainerLists hotelsContainer = new ContainerLists(hotelsList, ville, dateArrive, dateDepart, nbrPersonne);
        support.firePropertyChange("hotelsList", "", hotelsContainer);
    }


    /**
     * La methode vérifie si l'email correspondent à ce lui de la base de données avec le mot de passe relié à cet email
     *
     * @param email l'email de client
     * @param oldPassword l'ancien mot de passa verifier
     * @return si oui renvoi true, sinon false
     */
    @Override
    public boolean validateLogin(String email, String oldPassword) {
        return this.clientDAO.getValidation(email, oldPassword);
    }

    @Override
    public Client getClientByEmail(String email) {
        if (!email.isEmpty() || !email.isBlank()) {
            return this.clientDAO.getClientByEmail(email);
        }
        return null;
    }

    @Override
    public Client getClientByID(int id) {
        Client c = null;
        if (id != -1) {
            c = this.clientDAO.getClientByID(id);
        }
        return c;
    }

    @Override
    public boolean addClient(int idAdresse,
                             String nom,
                             String prenom,
                             String dateNaissance,
                             String email,
                             String numTel,
                             String password) {

        return this.clientDAO.addClient(idAdresse, nom, prenom, dateNaissance, email, numTel, 0, password);

    }

    @Override
    public Adresse getAdresseByID_model(int idAdresse) {
        return this.adressesDAO.getAdresseByID_dao(idAdresse);
    }


    @Override
    public void updateClientConnected(ArrayList<String> updatedClientList) {
        // verification supplémentaire de la list
        if (updatedClientList == null || updatedClientList.isEmpty()) {
            return;
        }
        // faire la mise ajour des données de client
        boolean isUpdated = this.clientDAO.updateClient(updatedClientList);

        // si les données de client sont bien mise ajour,
        // récupérer les données de son adresse
        if (isUpdated) {
            boolean isUpdatedAdresse =
                    this.adressesDAO.updateAdresse_dao(updatedClientList);
            if (isUpdatedAdresse) {
                // n'envoi PAS les mots de passe à l'interface via firePropertyChange
                updatedClientList.remove(9);
                updatedClientList.remove(8);
            }
        }
        support.firePropertyChange("updatedClientProfil", "", updatedClientList);
    }

    @Override
    public void getChambresByHotelId(String idHotel) {
        chambresList = chambreDAO.getChambre(Integer.parseInt(idHotel));

        if (chambresList.isEmpty()) {
            return;
        }
        support.firePropertyChange("chambresList", "", chambresList);
    }

    @Override
    public ArrayList<Equipement> getHotelEquipements(Hotel hotel) {
        ArrayList<Equipement> hotelEquipementsList;
            hotelEquipementsList = this.equipementDAO.getHotelEquipementsByHotelId(hotel);
            if(hotelEquipementsList.isEmpty()){
                return null;
            }
        return hotelEquipementsList;
    }

    @Override
    public Equipement getEquipementByHotelId(String hotelId) {
        return null;
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        return null;
    }

    @Override
    public void getOptions(String idHotel) {

    }


    @Override
    public ArrayList<String> getAllPays() {
        ArrayList<String> pays = new ArrayList<>();
        pays = adressesDAO.getAllPays();
        if (pays.isEmpty()) {
            return null;
        }
        return pays;
    }

    @Override
    public ArrayList<String> getAllVillesByPays(String pays) {
        ArrayList<String> villes = new ArrayList<>();
        villes = adressesDAO.getAllVilles(pays);
        if (villes.isEmpty()) {
            return null;
        }
        return villes;
    }

    @Override
    public ArrayList<String> getAllEquipements() {
        return this.equipementDAO.getAllEquipements();

    }

    @Override
    public ArrayList<String> getAllPrix() {
        return this.hotelDAO.getAllPrix();
    }



    // WORKS
    @Override
    public int addAdresse(String rue, String numRue, String boite, String ville, String codePostal, String pays) {

        return this.adressesDAO.addAdresse_dao(rue, numRue, boite, ville, codePostal, pays);
    }


    @Override
    public void close() {
        this.hotelDAO.close();
    }
}
