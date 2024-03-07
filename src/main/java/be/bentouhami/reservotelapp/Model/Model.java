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
import be.bentouhami.reservotelapp.Model.DAO.Details_reservation_option_hotelDAO.Details_reservation_option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Details_reservation_option_hotelDAO.IDetails_reservation_option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.EquipementDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.IEquipementDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.HotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.IHotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO.IOption_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO.Option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Reservations.IReservationDAO;
import be.bentouhami.reservotelapp.Model.DAO.Reservations.ReservationDAO;
import be.bentouhami.reservotelapp.Model.Services.Validator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model implements IModel {
    private ChambreDatas chambreData;
    private PropertyChangeSupport support;
    private IHotelDAO iHotelDAO;
    private IClientDAO iClientDAO;
    private IAdressesDAO iAdressesDAO;
    private HotelList hotelsList;
    private IChambreDAO iChambreDAO;
    private IEquipementDAO iEquipementDAO;
    private IOption_hotelDAO iOption_hotelDAO;
    private IDetailsReservationDAO iDetailsReservationDAO;
    private IReservationDAO iReservationDAO;
    private IDetails_reservation_option_hotelDAO iDetailsReservationOptionHotelDAO;
    private Reservation currentReservation;
    private DetailsReservationList currentDetailsReservationList;

    public Model() throws SQLException {
        this.initialize();
    }

    private void initialize() throws SQLException {
        this.support = new PropertyChangeSupport(this);
        this.iEquipementDAO = new EquipementDAO();
        this.iHotelDAO = new HotelDAO();
        this.iClientDAO = new ClientDAO();
        this.iAdressesDAO = new AdresseDAO();
        this.hotelsList = new HotelList();
        this.iChambreDAO = new ChambreDAO();
        this.iOption_hotelDAO = new Option_hotelDAO();
        this.chambreData = new ChambreDatas();
        this.iDetailsReservationDAO = new DetailsReservationDAO();
        this.iReservationDAO = new ReservationDAO();
        this.iDetailsReservationOptionHotelDAO = new Details_reservation_option_hotelDAO();
        this.currentDetailsReservationList = new DetailsReservationList();
        this.currentReservation = null;

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
    public Chambre getChambreByIdAndHotelId(String idChambre, String idHotel) {
        if (!Validator.isEmptyOrNullOrBlank(idChambre, idHotel)) {
            return this.iChambreDAO.getChambreByIdAndHotelId(Integer.parseInt(idChambre), Integer.parseInt(idHotel));
        }
        return null;
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
    public void writeReservationAndDetailsReservation(String[] searchingDatas,
                                                      ChambreDatas chambreInfos,
                                                      ArrayList<String[]> selectedOptions) {

        // vérification et validation des données
        if (chambreInfos == null||
                Validator.isEmptyOrNullOrBlank(searchingDatas) ||
                selectedOptions.isEmpty()) {
            return;
        }
        int idClient = Integer.parseInt(chambreInfos.getIdClient());

        // extraction des données de notre tableau searchingDatas pour les informations que le client a fournié
        String ville = searchingDatas[0];
        Date dateArriver = Date.valueOf(searchingDatas[1]);
        Date dateDepart = Date.valueOf(searchingDatas[2]);
        int nombrePersonneSouhaitee = Integer.parseInt(searchingDatas[3]);

        // récupération d'un objet Hotel de la DB
        Hotel hotel = getHotelById(chambreInfos.getIdHotel());

        // vérification si on a bien récupéré notre objet hotel
        if (hotel == null) return;

        // récupération d'un objet Chambre de la DB
        Chambre chambre = this.getChambreByIdAndHotelId(chambreInfos.getIdChambre(), String.valueOf(hotel.getIdHotel()));

        // vérification si on a bien récupéré notre objet chambre
        if (chambre == null) return;

        // creation d'un ArrayList des options sélectionnées
        ArrayList<Option> optionsList = this.getOptionsObjs(selectedOptions);

        // vérification si on a bien cree notre ArrayList des options
        if (isEmptyOption(optionsList)) return;

        // vérification si une nouvelle reservation : ou un autre hotel, ou un autre client connecté
        if (isNewReservationNeeded(hotel, idClient)) {
            finalizeCurrentReservation();
            initializeNewReservation();
        }


        // ajouter Details reservation a notre Reservation actuel
        addDetailsToCurrentReservation(selectedOptions, hotel, chambre, optionsList);

        // populate currentReservation
        this.populateCurrentReservation(idClient, hotel, dateArriver, dateDepart, nombrePersonneSouhaitee, ville);
        // Mettre à jour le prix total de la réservation en cours
        currentReservation.setPrixTotal(calculatePrixTotalReservation());
    }

    private void populateCurrentReservation(int idClient,
                                            Hotel hotel,
                                            Date dateArriver,
                                            Date dateDepart,
                                            int nombrePersonneSouhaitee, String ville) {
        currentReservation.setIdReservation(-1);
        currentReservation.setClientId(idClient);
        currentReservation.setHotel(hotel);
        currentReservation.setDateArrive(dateArriver);
        currentReservation.setDateDepart(dateDepart);
        currentReservation.setNombrePersonnes(nombrePersonneSouhaitee);
        currentReservation.setVille(ville);
    }


    private boolean isEmptyOption(ArrayList<Option> optionsList) {
        for (Option option : optionsList) {
            if (option == null) {
                return true;
            }
        }
        return false;
    }

    private boolean isNewReservationNeeded(Hotel hotel, int idClient) {
        return currentReservation == null ||
                currentReservation.getHotel() == null ||
                currentReservation.getHotel().getIdHotel() != hotel.getIdHotel() ||
                currentReservation.getClientId() != idClient;
    }

    private void finalizeCurrentReservation() {
        if (currentReservation != null && !currentDetailsReservationList.isEmpty()) {
            currentReservation.setDetailsReservationList(new ArrayList<>(currentDetailsReservationList));
        }
    }

    private double calculatePrixTotalReservation() {
        double prixTotalReservation = 0;
        for (DetailsReservation detailsReservation : currentDetailsReservationList) {
            prixTotalReservation += detailsReservation.getPrixTotal();
        }
        return prixTotalReservation;
    }

    private void initializeNewReservation() {
        this.currentReservation = new Reservation();
        this.currentDetailsReservationList = new DetailsReservationList();
    }

    private void addDetailsToCurrentReservation(ArrayList<String[]> selectedOptions,
                                                Hotel hotel,
                                                Chambre chambre,
                                                ArrayList<Option> optionsList) {

        // creation un object DetailReservation
        DetailsReservation detailsReservation = createDetailsReservation(selectedOptions, chambre, optionsList);

        // ajouter details reservation a notre list des DetailsReservationList
        currentDetailsReservationList.add(detailsReservation);
        currentReservation.setDetailsReservationList(currentDetailsReservationList);

    }

    private DetailsReservation createDetailsReservation(ArrayList<String[]> selectedOptions,
                                                        Chambre chambre,
                                                        ArrayList<Option> optionsList) {

        double prixTotalOptions = this.calculPrixOptions(selectedOptions);

        double prixTotalDetailsReservation = this.calculatePrixDetailsReservation(chambre.getPrix_chambre(), prixTotalOptions);

        return new DetailsReservation(-1, chambre, chambre.getPrix_chambre(), optionsList, prixTotalDetailsReservation);
    }


    public void finalizeReservation() {
        // Enregistrer la réservation dans la base de données
        int idReservation = iReservationDAO.writeReservation(currentReservation);
        currentReservation.setIdReservation(idReservation);

        // Enregistrer les détails de la réservation dans la base de données
        for (DetailsReservation detailsReservation : currentDetailsReservationList) {

            // enregistrement de detailsReservation current et récupération de l'id
            int idDetailsReservation = this.insertDetailsReservation(idReservation,
                    detailsReservation.getChambre().getId_chambre(),
                    detailsReservation.getPrixTotal());

            // mise ajoure l'id par default de cet detailsReservation
            detailsReservation.setIdDetailsReservation(idDetailsReservation);

            // parcourir la list des options de cet detailsReservation
            for (Option option : detailsReservation.getOptions()) {

                // enregistrement de les ids de l'option current dans la table de jointeur option_hotel et récupération de l'id
                int id_option_hotel = this.iOption_hotelDAO.getOption_HotelID(option.getId_option(),
                        currentReservation.getHotel().getIdHotel());

                // utiliser l'id option et l'id option_hotel pour populate la table de jointeur details_reservation_option_hotel
                this.populateDetailsReservationOptionHotel(id_option_hotel,
                        detailsReservation.getIdDetailsReservation());
            }
        }

        // Calculer le prix total de la réservation
        double prixTotal = calculatePrixTotalReservation();
        currentReservation.setPrixTotal(prixTotal);

        // Mettre à jour le prix total de la réservation dans la base de données
        updatePrixTotalReservation(currentReservation);

    }


    private void populateDetailsReservationOptionHotel(int idOptionHotel, int idDetailsReservation) {
        if(idOptionHotel > 0 && idDetailsReservation > 0){
            this.iDetailsReservationOptionHotelDAO.writeDetailsReservationOptionHotel(idOptionHotel, idDetailsReservation);
        }
    }


    private double calculatePrixTotalChambres(DetailsReservationList detailsReservationList) {

        double total = 0;
        for (DetailsReservation dtr : detailsReservationList) {
            total += dtr.getPrixTotal();
        }
        return total;
    }

    private int insertDetailsReservation(int reservationId, int chambre_id, double prixTotalDetailsReservation) {
        return this.iDetailsReservationDAO.writeDetailsReservation(reservationId,
                prixTotalDetailsReservation,
                chambre_id);
    }


    private void updatePrixTotalReservation(Reservation res) {
        // update prix total Reservation
        this.iReservationDAO.updatePrixTotalReservation(res.getIdReservation(), res.getPrixTotal());
    }

    private double calculPrixOptions(ArrayList<String[]> selectedOptions) {
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
    public void getAllReservations(String client_id) {
        ArrayList<String[]> reservations = this.iReservationDAO.getReservations(Integer.parseInt(client_id));
        this.support.firePropertyChange("showAllReservations", "", reservations);
    }

    @Override
    public void showRecapReservation() {
        //ArrayList<String> rs = currentReservation
        support.firePropertyChange("showRecapReservation", "", currentReservation);
    }

    /**
     * La méthode calcule le prix total de la chambre avec ses options
     *
     * @param prixChambres     prix total des chambre de la chambre
     * @param prixTotalOptions prix total des options
     * @return le total prix de la chambre + le total des prix des options
     */
    private double calculatePrixDetailsReservation(double prixChambres, double prixTotalOptions) {
        return prixChambres + prixTotalOptions;
    }

    private OptionList getOptionsObjs(ArrayList<String[]> selectedOptions) {
        OptionList options = new OptionList();
        for (String[] op : selectedOptions) {
            int option_id = Integer.parseInt(op[1]);
            String option = op[2];
            String description_option = op[3];
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


}
