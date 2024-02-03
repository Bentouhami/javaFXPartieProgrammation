package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.Adresse;
import be.bentouhami.reservotelapp.Model.BL.Client;
import be.bentouhami.reservotelapp.Model.BL.ClientConnecte;
import be.bentouhami.reservotelapp.Model.BL.HotelList;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.AdresseDAO;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.IAdressesDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.ClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.IClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.HotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.IHotelDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

public class Model implements IModel {
    private PropertyChangeSupport support;
    private IHotelDAO hotelDAO;
    private IClientDAO clientDAO;
    private IAdressesDAO adressesDAO;
    private ClientConnecte clientConnecte;
    private HotelList hotelsList;


    public Model() throws SQLException {
        this.support = new PropertyChangeSupport(this);
        this.hotelDAO = new HotelDAO();
        this.clientDAO = new ClientDAO();
        this.adressesDAO = new AdresseDAO();
        this.clientConnecte = new ClientConnecte();
        this.hotelsList = new HotelList();

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
        hotelsList = hotelDAO.getHotels(ville);
        support.firePropertyChange("hotelsList", "", this.hotelsList);
    }

    @Override
    public void logout() {

    }

    @Override
    public void showProfil() {

    }

    @Override
    public Client checkLogin(String email, String password){

        Client client = this.clientDAO.getClientByEmail(email);

        if(client != null){
                if(BCrypt.checkpw(password, client.getPassword()) && email.equals(client.getEmail())){
                        return new Client(client.getIdClient(),
                        client.getIdAdresse(),
                        client.getNom(),
                        client.getPrenom(),
                        client.getDateNaissance(),
                        client.getEmail(),
                        client.getNumeroTelephone(),
                        client.getPointsFidelite(),
                                password);
                }
        }
        return null;
    }

    // WORKS
    @Override
    public boolean addClient(int idAdresse,
                                String nom,
                                String prenom,
                                String dateNaissance,
                                String email,
                                String numTel,
                                String password) {

            this.clientDAO = new ClientDAO();

        return  this.clientDAO.addClient(idAdresse,
                nom,
                prenom,
                dateNaissance,
                email,
                numTel,
                0,
                password);

    }

    @Override
    public Adresse getAdresseByID(int idAdresse) {
       return this.adressesDAO.getAdresseByID(idAdresse);
    }

    // WORKS
    @Override
    public int addAdresse(String rue,
                              String numRue,
                              String boite,
                              String ville,
                              String codePostal,
                              String pays) {

        return this.adressesDAO.addAdresse(rue,
        numRue,
        boite,
        ville,
        codePostal,
        pays);

    }


    @Override
    public void close() {
        this.hotelDAO.close();
    }
}
