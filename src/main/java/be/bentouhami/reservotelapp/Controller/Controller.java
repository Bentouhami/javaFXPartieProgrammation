package be.bentouhami.reservotelapp.Controller;

import be.bentouhami.reservotelapp.Model.BL.Adresse;
import be.bentouhami.reservotelapp.Model.BL.Client;
import be.bentouhami.reservotelapp.Model.BL.ClientConnecte;
import be.bentouhami.reservotelapp.Model.IModel;
import be.bentouhami.reservotelapp.Model.Model;
import be.bentouhami.reservotelapp.View.IView;
import be.bentouhami.reservotelapp.View.PrimaryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Controller {

    private IModel model;
    private IView view;

    public void initialize() throws SQLException {
        this.model = new Model();
        this.view = new PrimaryView();
        if (PropertyChangeListener.class.isAssignableFrom(view.getClass())) {
            PropertyChangeListener pcl = (PropertyChangeListener) view;
            model.addPropertyChangeListener(pcl);

        }
        view.setController(this);
    }

    public void start() throws IOException {
        this.view.launchApp();
    }

    public EventHandler<ActionEvent> generateEventHandlerAction(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                function.accept(params.get());
            }

        };
    }

    public EventHandler<MouseEvent> generateEventHandlerMouse(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    function.accept(params.get());
                }
            }

        };
    }

    public EventHandler<WindowEvent> generateCloseEvent() {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                stop();
                System.exit(0);
            }
        };
    }

    private Consumer<String[]> generateConsumer(String action) {
        Consumer<String[]> t;
        switch (action) {
            case "show-hotels":
                t = (x) -> this.showHotelView(x[0], x[1], x[2], x[3]);
                break;

            case "showLoginView":
                t = (x) -> this.showLoginView();
                break;

            case "logout":
                t = (x) -> this.logout();
                break;

            case "showProfilView":
                t = (x) -> this.showProfil();
                break;
            case "checkClientData":
                t = (x) -> this.checkLogin(x[0], x[1]);

                break;
            case "showInscriptionView":
                t = (x) -> this.view.showInscription();
                break;
            case "addNewClientWithAdresse":
                t = (x) -> this.addNewClientWithAdresse(
                        x[0],
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
                break;


            default:
                throw new InvalidParameterException(action + " n'existe pas.");
        }
        return t;
    }

    private void addNewClientWithAdresse(String nom,
                                         String prenom,
                                         String date_naissance,
                                         String email,
                                         String num_tel,
                                         String password,
                                         String verifyPassword,
                                         String rue,
                                         String numRue,
                                         String boite,
                                         String ville,
                                         String code_postal,
                                         String pays) {

        if (nom.isEmpty() || nom.isBlank() ||
                prenom.isEmpty() || prenom.isBlank() ||
                num_tel.isEmpty() || num_tel.isBlank() ||
                email.isEmpty() || email.isBlank() ||
                password.isEmpty() || password.isBlank() ||
                verifyPassword.isEmpty() || verifyPassword.isBlank() ||
                rue.isEmpty() || rue.isBlank() ||
                numRue.isEmpty() || numRue.isBlank() ||
                boite.isEmpty() || boite.isBlank() ||
                code_postal.isEmpty() || code_postal.isBlank() ||
                ville.isEmpty() || ville.isBlank() ||
                pays.isEmpty() || pays.isBlank()) {

            this.view.showAlert(Alert.AlertType.ERROR,
                    "Tous les champs sont obigatoire",
                    ButtonType.OK);
        } else if (!password.equals(verifyPassword)) {
            this.view.showAlert(Alert.AlertType.ERROR,
                    "Les mots de passe ne correspondent pas",
                    ButtonType.OK);
            //this.view.clearInscriptionData();
        } else {
            // ajouter un adresse et récuperer son id
            int idAdresse = this.model.addAdresse(rue, numRue, boite, code_postal, ville, pays);
            if (idAdresse == -1) {
                view.showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de l'adresse", ButtonType.OK);
                return;
            }
            boolean success = this.model.addClient(idAdresse,
                    nom,
                    prenom,
                    date_naissance,
                    email,
                    num_tel,
                    password);
            if (success) {

                view.showAlert(Alert.AlertType.CONFIRMATION, "Bienvenu a Reservotel, vous pouvez utiliser ton compte", ButtonType.OK);
                this.showLoginView();
            } else {
                view.showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de client", ButtonType.OK);
                this.showLoginView();
            }

        }

    }


    private void checkLogin(String email, String password) {
        if (email.isEmpty() || email.isBlank() &&
                password.isEmpty() || password.isBlank()) {
            this.view.showAlert(Alert.AlertType.ERROR, "Erreur, email ou mot de passe sont obligatoire ", ButtonType.OK);
        } else {

            Client client = this.model.checkLogin(email, password);
            if (client != null) {
                Adresse adresseClient = this.model.getAdresseByID(client.getIdAdresse());
                if (adresseClient != null) {
                    ClientConnecte clientConnecte = new ClientConnecte(client);

                    if (clientConnecte != null) {
                        this.view.showProfilView(clientConnecte.getClientConnecte().getNom(),
                                clientConnecte.getClientConnecte().getPrenom(),
                                clientConnecte.getClientConnecte().getDateNaissance().toString(),
                                clientConnecte.getClientConnecte().getEmail(),
                                clientConnecte.getClientConnecte().getNumeroTelephone(),
                                clientConnecte.getClientConnecte().getPointsFidelite(),
                                password,
                                adresseClient.getRue(),
                                adresseClient.getNumero(),
                                adresseClient.getBoite(),
                                adresseClient.getVille(),
                                adresseClient.getCodePostal(),
                                adresseClient.getPays());

                    } else {
                        this.view.showAlert(Alert.AlertType.ERROR, "Impossible de mise ajoure le client connecte compte! ", ButtonType.OK);
                    }
//                this.view.showProfilView(client.getNom(),
//                        client.getPrenom(),
//                        client.getDateNaissance().toString(),
//                        client.getNumeroTelephone(),
//                        client.getEmail(),
//                        password,
//                        adresseClient.getRue(),
//                        adresseClient.getNumero(),
//                        adresseClient.getBoite(),
//                        adresseClient.getCodePostal(),
//                        adresseClient.getVille(),
//                        adresseClient.getPays());
                } else {
                    this.view.showAlert(Alert.AlertType.ERROR, "Impossible de trouver un adresse valid pour ce cleint, Contactez le Service ", ButtonType.OK);
                }

            } else {
                this.view.showAlert(Alert.AlertType.ERROR, "Le mot de passe ou le mot de passe n'est pas valid. ", ButtonType.OK);
            }


        }


    }

    private void showProfil() {

        //this.view.showProfil();
    }

    private void logout() {
        this.model.logout();
    }

    private void showLoginView() {
        this.view.showLoginView();
    }


    private void showHotelView(String ville, String dateArrive, String dateDepart, String nbrPersonne) {
        this.model.showHotels(ville, dateArrive, dateDepart, nbrPersonne);
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

    public void onSeConnecterClicked(ActionEvent actionEvent) {
        System.out.println("btn conencter");
    }

}// end class
