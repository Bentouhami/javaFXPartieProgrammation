package be.bentouhami.reservotelapp.Controller;

import be.bentouhami.reservotelapp.Model.BL.*;
import be.bentouhami.reservotelapp.Model.IModel;
import be.bentouhami.reservotelapp.Model.Model;
import be.bentouhami.reservotelapp.Model.Services.Validator;
import be.bentouhami.reservotelapp.View.IView;
import be.bentouhami.reservotelapp.View.PrimaryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Controller {

    private IModel model;
    private IView view;

    public void initialize() throws SQLException {
        this.model = new Model();
        this.view = new PrimaryView();
        PropertyChangeListener.class.isAssignableFrom(view.getClass());
        PropertyChangeListener pcl = (PropertyChangeListener) view;
        model.addPropertyChangeListener(pcl);

        view.setController(this);
    }

    public void start() throws IOException {
        this.view.launchApp();
    }

    public EventHandler<ActionEvent> generateEventHandlerAction(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return arg0 -> function.accept(params.get());
    }

    public EventHandler<MouseEvent> generateEventHandlerMouseOnce(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return arg0 -> {
            if (arg0.getClickCount() == 1) {
                function.accept(params.get());
            }
        };
    }

    public EventHandler<MouseEvent> generateEventHandlerMouseTwise(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return new EventHandler<>() {
            @Override
            public void handle(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    function.accept(params.get());
                }
            }
        };
    }

    public EventHandler<WindowEvent> generateCloseEvent() {
        return t -> {
            stop();
            System.exit(0);
        };
    }

    private Consumer<String[]> generateConsumer(String action) {
        return switch (action) {
            case "show-hotels" -> (x) -> this.showHotelView(x[0],
                    x[1],
                    x[2],
                    x[3]);

            case "addNewClientWithAdresse" -> (x) -> this.addNewClientWithAdresse(x[0],
                    x[1],
                    x[2],
                    x[3],
                    x[4],
                    x[5],
                    x[6],
                    x[7],
                    x[8],
                    x[9],
                    x[10],
                    x[11],
                    x[12]);

            case "updateClientConnectedProfil" -> (x) -> this.updateClientConnectedProfil(x[0], // id_client . 0
                    x[1], // adresse_id . 1
                    x[2], // nom_client . 2
                    x[3], // prenom . 3
                    x[4], // date de naissance . 4
                    x[5], // email de client . 5
                    x[6], // numero de telephone  . 6
                    x[7], // points de fidelite . 7
                    x[8], // Old password . 8
                    x[9], // New password . 9
                    x[10], // Rue . 10
                    x[11], // numRue . 11
                    x[12], // boite . 12
                    x[13], // ville . 13
                    x[14], // codepostal . 14
                    x[15]); // pays . 15
            case "updatePassword" -> (x) -> this.updatePassword(x[0], x[1], x[2]);
            case "showChambres" -> (x) -> this.showChambresView(x[0], x[1], x[2], x[3]);
            case "showChambreDatas" -> (x) -> this.showChambreDatas(x[0], x[1], x[2]);
            default -> throw new InvalidParameterException(action + " n'existe pas.");
        };
    }

    private void showChambreDatas(String idClient, String idHotel, String idChambre) {
        if (Validator.isNotEmpty(idClient, idHotel, idChambre)) {
            ArrayList<String[]> options = this.model.getOptions(idHotel);
            this.model.getChambreDatas(idClient, idHotel, idChambre, options);
        } else {
            this.view.showAlert(Alert.AlertType.ERROR, "Un hôtel et une ou plusieurs chambres doivent être sélectionnés", ButtonType.OK);
        }
    }

    private void updatePassword(String email, String numeroTelephone, String newPassword) {
        if (Validator.isNotEmpty(email, numeroTelephone, newPassword)) {
            // récuperation de l'objet client via son email
            Client c = this.model.getClientByEmail(email);
            int id_client = 0;
            if (!(c == null)) {
                id_client = c.getIdClient();
            }
            boolean isValidPhone = false;
            if (Validator.isValidEmail(email) && Validator.isValidPhone(numeroTelephone) && Validator.isValidPassword(newPassword)) {
                isValidPhone = this.model.validatePhone(id_client, email, numeroTelephone);
            } else {
                this.view.showAlert(Alert.AlertType.ERROR,
                        "Votre email ou mot de passe n'est pa valid!",
                        ButtonType.OK);
            }
            if (isValidPhone) {
                boolean isUpdatedPassword = this.model.updatePassword(id_client, newPassword);
                if (isUpdatedPassword) {
                    this.view.showAlert(Alert.AlertType.CONFIRMATION,
                            "Votre mot passe est a jour",
                            ButtonType.OK);
                } else {
                    this.view.showAlert(Alert.AlertType.ERROR,
                            "Impossible de faire la mise ajoure de mot de passe, ressayer!",
                            ButtonType.OK);
                }
            } else {
                this.view.showAlert(Alert.AlertType.ERROR,
                        "votre numéro de telephone n'est pas valid ou n'existe pas!",
                        ButtonType.OK);
            }
        } else {
            this.view.showAlert(Alert.AlertType.ERROR,
                    "Vérifier les champs obligatoire",
                    ButtonType.OK);
        }
    }

    private void showOptionsList(String idHotel) {
        this.view.showOptionsView(getOptionsList(idHotel));
    }

    private ArrayList<String[]> getOptionsList(String idHotel) {
        return this.model.getOptions(idHotel);
    }


    private void showChambresView(String id_hotel, String dateArr, String dateDep, String nbrPer) {
        if (Validator.isNotEmpty(id_hotel)) {
            this.model.getChambresByHotelId(id_hotel);
            // affichage de la liste des options
            this.showOptionsList(id_hotel);
        } else {
            this.view.showAlert(Alert.AlertType.ERROR,
                    "Cet hôtel n'existe pas. Veuillez sélectionner un autre hôtel.",
                    ButtonType.OK);
        }
    }


    private void updateClientConnectedProfil(String... clientNewValues) {

        // parcourir les données pour la verification qu'ils ne sont pas null
        // (si oui Alert erreur, sinon ajouter dans la list)
        String email = clientNewValues[5];
        String oldPasswd = clientNewValues[8];
        String newPasswd = clientNewValues[9];
        if (!Validator.isValidEmail(email) || !Validator.isValidPassword(oldPasswd) || !Validator.isValidPassword(newPasswd)) {
            this.view.showAlert(Alert.AlertType.ERROR, "Verifier vos données que ne sont pas vide ", ButtonType.OK);
            return;
        }
        // convertir le tableau de String en une ArrayList
        ArrayList<String> clientNewDatasList = new ArrayList<>(Arrays.stream(clientNewValues).toList());

        // utilisation des noms facile pour les passes en parameters
        String email_client = clientNewDatasList.get(5);
        String oldPassword = clientNewDatasList.get(8);
        String newPassword = clientNewDatasList.get(9);

        // verification si l'email et l'ancien mot de passe correspondent
        // ET que le nouveau mot de passe n'est pas null
        // (si oui faire la mise ajour de client, sinon Alert erreur)
        boolean isValidLogin = this.isValidLogin(email_client, oldPassword);
        if (isValidLogin && !newPassword.isEmpty()) {
            this.model.updateClientConnected(clientNewDatasList);
        } else {
            this.view.showAlert(Alert.AlertType.ERROR, "Verifier votre email ou mot de passe", ButtonType.OK);
            this.view.showProfilView(clientNewDatasList);
            return;
        }
        // si tout passe bien Alert confirmation
        this.view.showAlert(Alert.AlertType.CONFIRMATION, "Vos données sont mise ajour", ButtonType.OK);
    }// end method

    private void addNewClientWithAdresse(String nom,
                                         String prenom,
                                         String date_naissance,
                                         String num_tel,
                                         String email,
                                         String password,
                                         String verifyPassword,
                                         String rue,
                                         String numRue,
                                         String boite,
                                         String ville,
                                         String code_postal,
                                         String pays) {

        if (nom.isEmpty() ||
                nom.isBlank() ||
                prenom.isEmpty() ||
                prenom.isBlank() ||
                num_tel.isEmpty() ||
                num_tel.isBlank() ||
                email.isEmpty() ||
                email.isBlank() ||
                password.isEmpty() ||
                password.isBlank() ||
                verifyPassword.isEmpty() ||
                verifyPassword.isBlank() ||
                rue.isEmpty() ||
                rue.isBlank() ||
                numRue.isEmpty() ||
                numRue.isBlank() ||
                boite.isEmpty() ||
                boite.isBlank() ||
                code_postal.isEmpty() ||
                code_postal.isBlank() ||
                ville.isEmpty() ||
                ville.isBlank() ||
                pays.isEmpty() ||
                pays.isBlank()) {

            this.view.showAlert(Alert.AlertType.ERROR, "Tous les champs sont obligatoire", ButtonType.OK);
        } else if (!password.equals(verifyPassword)) {
            this.view.showAlert(Alert.AlertType.ERROR, "Les mots de passe ne correspondent pas", ButtonType.OK);
            //this.view.clearInscriptionData();
        } else {
            // ajouter une adresse et récupérer son id
            int idAdresse = this.model.addAdresse(rue, numRue, boite, code_postal, ville, pays);
            if (idAdresse == -1) {
                view.showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de l'adresse", ButtonType.OK);
                return;
            }
            boolean success = this.model.addClient(idAdresse, nom, prenom, date_naissance, email, num_tel, password);
            if (success) {
                view.showAlert(Alert.AlertType.CONFIRMATION, "Bienvenu a Reservotel, vous pouvez utiliser ton compte", ButtonType.OK);
                this.showLoginView();
            } else {
                view.showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de client", ButtonType.OK);
                this.showLoginView();
            }
        }
    }

    public ArrayList<Equipement> getHotelEquipementsByHotelId(Hotel hotel) {
        ArrayList<Equipement> hotelEq = new ArrayList<>();

        if (!Validator.isNotEmpty(String.valueOf(hotel.getIdHotel()))) {
            this.view.showAlert(Alert.AlertType.ERROR,
                    "Cet hôtel n'existe pas. Veuillez sélectionner un autre hôtel.",
                    ButtonType.OK);

        } else {
            // récuperation des équipements de hotel
            hotelEq = this.model.getHotelEquipements(hotel);
        }
        return hotelEq;
    }

    public ArrayList<String> checkClientData(String email, String password) {

        ArrayList<String> clientConnectedDatas = new ArrayList<>();
        // verifier si les champs ne sont pas null ou vide
        if (!Validator.isNotEmpty(email, password)) {
            this.view.showAlert(Alert.AlertType.ERROR, "Erreur, email ou mot de passe sont obligatoire ", ButtonType.OK);
        }
        // Sinon vérifier si l'email et le mot de passe respectant les contraints
        boolean validEmail = Validator.isValidEmail(email);
        boolean validPasswd = Validator.isValidPassword(password);
        if (validEmail && validPasswd && isValidLogin(email, password)) {
            Client c = this.model.getClientByEmail(email);
            Adresse adresseClient = this.model.getAdresseByID_model(c.getIdAdresse());
            // Création de la liste des données du client
            // creation une arrayList pour stocker toutes les informations de client connecté combine les informations de client et son adresse

            // Client infos
            clientConnectedDatas.add(String.valueOf(c.getIdClient())); // id_client . 0
            clientConnectedDatas.add(String.valueOf(adresseClient.getIdAdresse())); // adresse_id . 1
            clientConnectedDatas.add(c.getNom()); // nom_client . 2
            clientConnectedDatas.add(c.getPrenom()); // prenom . 3
            clientConnectedDatas.add(c.getDateNaissance().toString()); // date de naissance . 4
            clientConnectedDatas.add(c.getEmail()); // email . 5
            clientConnectedDatas.add(c.getNumeroTelephone()); // numero de telephone . 6
            clientConnectedDatas.add(String.valueOf(c.getPointsFidelite())); // points de fidelite . 7

            // adresse infos
            clientConnectedDatas.add(adresseClient.getRue()); // Rue . 8
            clientConnectedDatas.add(adresseClient.getNumero()); // numRue . 9
            clientConnectedDatas.add(adresseClient.getBoite()); // boite . 10
            clientConnectedDatas.add(adresseClient.getVille()); // ville . 11
            clientConnectedDatas.add(adresseClient.getCodePostal()); // codepostal . 12
            clientConnectedDatas.add(adresseClient.getPays()); // pays . 13

            // Appel de showProfilView avec les données du client
            this.view.showAlert(Alert.AlertType.INFORMATION, c.getNom() + " " + c.getPrenom() + " Bienvenu parmi nous", ButtonType.OK);
            this.view.showProfilView(clientConnectedDatas);
        } else {
            this.view.showAlert(Alert.AlertType.ERROR, "L'email ou le mot de passe n'est pas valide.", ButtonType.OK);
        }
        return clientConnectedDatas;

    }

    public boolean isValidLogin(String email, String pass) {
        return this.model.validateLogin(email, pass);
    }


    public void showLoginView() {
        this.view.showLoginView();
    }

    private void showHotelView(String... infoHotel) {
        if (!Validator.isNotEmpty(infoHotel)) {
            this.view.showAlert(Alert.AlertType.ERROR, "Les valeur ne doivent pas être null ou vide", ButtonType.OK);
            return;
        }
        this.model.getHotels(infoHotel[0], infoHotel[1], infoHotel[2], infoHotel[3]);
    }

    public ArrayList<String> getAllPays() {
        return this.model.getAllPays();
    }

    public ArrayList<String> getAllVillesByPays(String pays) {
        return this.model.getAllVillesByPays(pays);
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    public void setView(IView view) {
        this.view = view;
    }

    public void stop() {
        this.model.close();
        this.view.stopApp();
    }

    public void writeReservationAndDetailsReservation(Button btn_ajouterRes,
                                                      String client_id,
                                                      String[] hotelSearchDatas, // données de la recherche ville - dates - nombre personnes
                                                      ArrayList<String> selectedChambre, // id chambre sélectionnée
                                                      ArrayList<String[]> selectedOptions) {  // list des options sélectionnées pour cette chambre

        // Vérification des données
        if (Validator.isNotEmpty(hotelSearchDatas) &&
                Validator.isNotEmpty(client_id) &&
                !selectedChambre.isEmpty() &&
                !selectedOptions.isEmpty()) {

            Reservation res = this.model.writeReservationAndDetailsReservation(
                    client_id,
                    hotelSearchDatas,
                    selectedChambre,
                    selectedOptions);

            boolean isAdd = this.view.showAddNewChambre(btn_ajouterRes,
                    "Voulez-vous ajouter une autre chambre ?");
            if(isAdd){
                this.view.showChambresList();
            } else {
                this.showReservationRecap(this.model.getReservation());
            }

        }
    }
    public void showReservationRecap(ReservationList reservationList) {
        this.view.showReservationRecap(reservationList);
    }
}// end class
