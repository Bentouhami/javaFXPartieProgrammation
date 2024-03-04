package be.bentouhami.reservotelapp.Model;

import be.bentouhami.reservotelapp.Model.BL.*;
import be.bentouhami.reservotelapp.Model.BL.Containers.ChambreDatas;
import be.bentouhami.reservotelapp.Model.BL.Containers.ContainerLists;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.AdresseDAO;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.IAdressesDAO;
import be.bentouhami.reservotelapp.Model.DAO.Chambres.ChambreDAO;
import be.bentouhami.reservotelapp.Model.DAO.Chambres.IChambreDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.ClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.IClientDAO;
import be.bentouhami.reservotelapp.Model.DAO.DetailsReservations.DetailsReservationDAO;
import be.bentouhami.reservotelapp.Model.DAO.DetailsReservations.IDetailsReservationDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.EquipementDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.IEquipementDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.HotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.IHotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO.IOption_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO.Option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Options.IOptionDAO;
import be.bentouhami.reservotelapp.Model.DAO.Options.OptionDAO;
import be.bentouhami.reservotelapp.Model.DAO.Reservations.IReservationDAO;
import be.bentouhami.reservotelapp.Model.DAO.Reservations.ReservationDAO;
import be.bentouhami.reservotelapp.Model.Services.Validator;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model implements IModel {
    private final IOptionDAO iOptionDAO;
    private ChambreDatas chambreData;
    private final PropertyChangeSupport support;
    private final IHotelDAO iHotelDAO;
    private final IClientDAO iClientDAO;
    private final IAdressesDAO iAdressesDAO;
    private HotelList hotelsList;
    private final OptionList options;
    private ChambreList chambresList;
    private final IChambreDAO iChambreDAO;
    private final IEquipementDAO iEquipementDAO;
    private IOption_hotelDAO iOption_hotelDAO;
    private DetailsReservation detailsReservation;
    private IDetailsReservationDAO iDetailsReservationDAO;
    private DetailsReservationList detailsReservationList;
    private OptionList selectedOptionsList;
    private ReservationList reservationList;
    private IReservationDAO iReservationDAO;

    public Model() throws SQLException {
        this.support = new PropertyChangeSupport(this);
        this.iEquipementDAO = new EquipementDAO();
        this.iHotelDAO = new HotelDAO();
        this.iClientDAO = new ClientDAO();
        this.iAdressesDAO = new AdresseDAO();
        this.hotelsList = new HotelList();
        this.iChambreDAO = new ChambreDAO();
        this.chambresList = new ChambreList();
        this.iOptionDAO = new OptionDAO();
        this.options = new OptionList();
        this.iOption_hotelDAO = new Option_hotelDAO();
        this.chambreData = new ChambreDatas();
        this.iDetailsReservationDAO = new DetailsReservationDAO();
        this.iReservationDAO = new ReservationDAO();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public void getHotels(String ville,
                          String dateArrive,
                          String dateDepart,
                          String nbrPersonne) {

        hotelsList = this.iHotelDAO.getHotels(ville);
        ContainerLists hotelsContainer = new ContainerLists(hotelsList, ville, dateArrive, dateDepart, nbrPersonne);
        support.firePropertyChange("hotelsList", "", hotelsContainer);
    }

    /**
     * La methode vérifie si l'email correspondent à ce lui de la base de données avec le mot de passe relié à cet email
     *
     * @param email       l'email de client
     * @param oldPassword l'ancien mot de passa verifier
     * @return si oui renvoi true, sinon false
     */
    @Override
    public boolean validateLogin(String email, String oldPassword) {
        return this.iClientDAO.getValidation(email, oldPassword);
    }

    @Override
    public Client getClientByEmail(String email) {
        if (!email.isEmpty() || !email.isBlank()) {
            return this.iClientDAO.getClientByEmail(email);
        }
        return null;
    }

    @Override
    public boolean addClient(int idAdresse,
                             String nom,
                             String prenom,
                             String dateNaissance,
                             String email,
                             String numTel,
                             String password) {

        return this.iClientDAO.addClient(idAdresse, nom, prenom, dateNaissance, email, numTel, 0, password);

    }

    @Override
    public Adresse getAdresseByID_model(int idAdresse) {
        return this.iAdressesDAO.getAdresseByID_dao(idAdresse);
    }


    @Override
    public void updateClientConnected(ArrayList<String> updatedClientList) {
        // verification supplémentaire de la list
        if (updatedClientList == null || updatedClientList.isEmpty()) {
            return;
        }
        // faire la mise ajour des données de client
        boolean isUpdated = this.iClientDAO.updateClient(updatedClientList);

        // si les données de client sont bien mise ajour,
        // récupérer les données de son adresse
        if (isUpdated) {
            boolean isUpdatedAdresse =
                    this.iAdressesDAO.updateAdresse_dao(updatedClientList);
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
        ArrayList<String[]> chambresList;
        chambresList = iChambreDAO.getChambresListByHotelId(Integer.parseInt(idHotel));

        if (chambresList.isEmpty()) {
            return;
        }
        support.firePropertyChange("chambresList", "", chambresList);
    }

    @Override
    public ArrayList<Equipement> getHotelEquipements(Hotel hotel) {
        ArrayList<Equipement> hotelEquipementsList;
        hotelEquipementsList = this.iEquipementDAO.getHotelEquipementsByHotelId(hotel);
        if (hotelEquipementsList.isEmpty()) {
            return null;
        }
        return hotelEquipementsList;
    }


    @Override
    public ArrayList<String[]> getOptions(String idHotel) {
        ArrayList<String[]> options = this.iOption_hotelDAO.getOptionsByHotelId(idHotel);
        support.firePropertyChange("showOptions", "", options);
        return options;

    }

    @Override
    public boolean validatePhone(int idClient, String email, String numeroTelephone) {
        return this.iClientDAO.isValidPhone(idClient, numeroTelephone);
    }

    @Override
    public boolean updatePassword(int idClient, String newPassword) {

        return this.iClientDAO.updatePassword(idClient, newPassword);
    }

    @Override
    public void getChambreDatas(String idClient,
                                String idHotel,
                                String idChambre,
                                ArrayList<String[]> options) {
        ArrayList<String> chambresDetails = this.iChambreDAO.getChambreDatadByIdAndHotelId(Integer.parseInt(idChambre),
                Integer.parseInt(idHotel));
        this.chambreData = new ChambreDatas(idClient, idHotel, idChambre, chambresDetails);
        chambreData.addOption(options);
        support.firePropertyChange("showSelectedChambreDatas", "", chambreData);
    }

    @Override
    public Chambre getChambreByIdAndHotelId(String id_chambre, String id_hotel) {
        if (!Validator.isNotEmpty(id_chambre, id_hotel)) {
            int chambre_id = Integer.parseInt(id_chambre);
            int hotel_id = Integer.parseInt(id_hotel);
            return this.iChambreDAO.getChambreByIdAndHotelId(chambre_id, hotel_id);
        } else {
            return null;
        }
    }

    @Override
    public Reservation writeReservationAndDetailsReservation(String idClient,
                                                             String[] searchingDatas,
                                                             ArrayList<String> chambreInfos,
                                                             ArrayList<String[]> selectedOptions) {

        if (this.detailsReservationList == null) {
            this.detailsReservationList = new DetailsReservationList();
        }
        if (this.reservationList == null) {
            this.reservationList = new ReservationList();
        }

        Reservation res = null;
        int id_detailsReservation = -1;
        int reservation_id = -1;
        // vérification et validation des données
        if (Validator.isNotEmpty(idClient) &&
                Validator.isNotEmpty(searchingDatas) &&
                !chambreInfos.isEmpty() &&
                !selectedOptions.isEmpty()) {

            // récuperation des données de la chambre sélectionnée
            String id_chambre = chambreInfos.get(0);
            String id_hotel = chambreInfos.get(1);
            String numeroChambre = chambreInfos.get(2);
            String etage = chambreInfos.get(3);
            String nombreMaxPersonnesParChambre = chambreInfos.get(4);
            String estDisponible = chambreInfos.get(5);
            String photoChambre = chambreInfos.get(6);
            String typeChambre = chambreInfos.get(7);
            String lits = chambreInfos.get(8);
            String prixChambre = chambreInfos.get(9);

            // récupérer hotel sélectionnée
            Hotel h = this.iHotelDAO.getHotelById(id_hotel);
            int chambre_id = Integer.parseInt(id_chambre);
            int hotel_id = Integer.parseInt(id_hotel);

            // récupérer la chambre sélectionnée
            Chambre ch = this.iChambreDAO.getChambreByIdAndHotelId(chambre_id, hotel_id);

            // récupérer l'id_client en int
            int client_id = Integer.parseInt(idClient);

            // récupérer la ArrayList OptionList à partir de la ArrayList String[] de selectedOptions
            if (this.selectedOptionsList == null) {
                this.selectedOptionsList = new OptionList();
            }
            this.selectedOptionsList = this.getOptionsObjs(selectedOptions);

            // récupérer les prix de chaque option et calculer le total de prix options
            double prixTotalDesOptions = this.calculateOptionsPrixTotal(selectedOptions);

            // calculate et return prix de la chambre + prix de ses options (prix total detailsReservation)
            double prixTotalDetailsReservation = this.calculatePrixTotalDetailsReservation(ch.getPrix_chambre(), prixTotalDesOptions);

            // récupérer les données de la recherche de client
            String ville = searchingDatas[0];
            Date dateArriver = Date.valueOf(searchingDatas[1]);
            Date dateDepart = Date.valueOf(searchingDatas[2]);
            int nombrePersonne = Integer.parseInt(searchingDatas[3]);

            // insertion d'une reservation et la récupération de son id
            reservation_id = this.iReservationDAO.writeReservation(client_id, dateArriver, dateDepart);

            // creation d'un objet Reservation contient les données récupérées
            res = getReservation(reservation_id, h, dateArriver, dateDepart, prixTotalDetailsReservation, nombrePersonne, ville);

            // Insertion une detailReservation dans la DB et récupérer son id
            id_detailsReservation = getIdDetailsReservation(res, ch);

            // creation d'un objet DetailsReservation avec les données récupérées
            DetailsReservation detailsReservation = getDetailsReservation(id_detailsReservation, ch, prixTotalDetailsReservation);

            // ajouter detailsReservation a la liste des DetailsReservationList
            this.detailsReservationList.add(detailsReservation);

            // mis ajour l'objet reservation avec la liste des detailsReservationList
            res.setDetailsReservation(this.detailsReservationList);

            // calculer le prixTotal de toutes les detailsReservationList
            res.setPrixTotal(getPrixTotalReservation(this.detailsReservationList));

            // update prix total Reservation
            boolean isUpdatedPrix = updateprixTotalReservation(res);
            if (isUpdatedPrix) {
                this.reservationList.add(res);
            }
        }

        return res;
    }

    @NotNull
    private Reservation getReservation(int reservation_id, Hotel h, Date dateArriver, Date dateDepart, double prixTotalDetailsReservation, int nombrePersonne, String ville) {
        Reservation res;
        // creation un objet reservation
        res = new Reservation(reservation_id,
                h,
                dateArriver,
                dateDepart,
                prixTotalDetailsReservation,
                nombrePersonne,
                ville,
                this.detailsReservationList);
        return res;
    }

    private int getIdDetailsReservation(Reservation res, Chambre ch) {

        // insert detailsReservation et la récupération de l'id
        return this.iDetailsReservationDAO.writeDetailsReservations(res.getIdReservation(),
                ch.getPrix_chambre(),
                ch.getId_chambre());
    }

    @NotNull
    private DetailsReservation getDetailsReservation(int id_detailsReservation, Chambre ch, double prixTotalDetailsReservation) {
        // creation de l'objet detailsReservation
        return new DetailsReservation(id_detailsReservation,
                ch,
                ch.getPrix_chambre(),
                this.selectedOptionsList,
                prixTotalDetailsReservation);
    }

    private boolean updateprixTotalReservation(Reservation res) {
        // update prix total Reservation
        return this.iReservationDAO.updatePrixTotalReservation(res.getIdReservation(), res.getPrixTotal());
    }

    private double getPrixTotalReservation(DetailsReservationList detailsReservationList) {
        double prixTotalReservation = 0;

        for (DetailsReservation dtr : detailsReservationList) {
            prixTotalReservation += dtr.getPrixTotal();
        }
        return prixTotalReservation;
    }

    private double calculateOptionsPrixTotal(ArrayList<String[]> selectedOptions) {
        double total = 0;
        for (String[] option : selectedOptions) {
            // récupérer le prix de chaque option par l'id d'hotel et l'id option
            double prixOption = Double.parseDouble(option[4]);
            total += prixOption;
        }
        // retourner le prix total de toutes les options
        return total;
    }

    @Override
    public Hotel getHotelById(String hotel_id) {
        return this.iHotelDAO.getHotelById(hotel_id);
    }

    @Override
    public ReservationList getAllReservations(String client_id) {
        return this.iReservationDAO.getReservations(Integer.parseInt(client_id));
    }

    @Override
    public ReservationList getReservation() {
        support.firePropertyChange("showReservationDatas", "", this.reservationList);
        return this.reservationList;
    }

    /**
     * Calcule le prix total pour la reservation
     *
     * @param detailsReservationArrList une arrayList des arrayList<DetailsReservation>
     * @return le prix total de la reservation.
     */
    private double calculatePrixTotalReservation(ArrayList<DetailsReservationList> detailsReservationArrList) {
        double prixTotalReservation = 0;
        for (DetailsReservationList detailsReservationList : detailsReservationArrList) {
            for (DetailsReservation detailsReservation : detailsReservationList) {
                prixTotalReservation += detailsReservation.getPrixTotal();
            }
        }
        return prixTotalReservation;
    }

    /**
     * La méthode calcule le prix total de la chambre avec ses options
     *
     * @param prixChambre      prix de la chambre
     * @param prixTotalOptions prix total des options
     * @return le total prix de la chambre + le total des prix des options
     */
    private double calculatePrixTotalDetailsReservation(double prixChambre, double prixTotalOptions) {
        return prixChambre + prixTotalOptions;
    }

    private OptionList getOptionsObjs(ArrayList<String[]> selectedOptions) {
        OptionList options = new OptionList();
        for (String[] op : selectedOptions) {
            int option_id = Integer.parseInt(op[0]);
            String description_option = op[1];
            String option = op[2];
            options.add(new Option(option_id, description_option, option));

        }
        return options;
    }

    public ArrayList<String> getAllPays() {
        ArrayList<String> pays = new ArrayList<>();
        pays = iAdressesDAO.getAllPays();
        if (pays.isEmpty()) {
            return null;
        }
        return pays;
    }

    @Override
    public ArrayList<String> getAllVillesByPays(String pays) {
        ArrayList<String> villes = new ArrayList<>();
        villes = iAdressesDAO.getAllVilles(pays);
        if (villes.isEmpty()) {
            return null;
        }
        return villes;
    }

    // WORKS
    @Override
    public int addAdresse(String rue,
                          String numRue,
                          String boite,
                          String ville,
                          String codePostal,
                          String pays) {

        return this.iAdressesDAO.addAdresse_dao(rue,
                numRue,
                boite,
                ville,
                codePostal,
                pays);
    }


    @Override
    public void close() {
        this.iHotelDAO.close();
    }

    public OptionList getOptions() {
        return options;
    }


}
