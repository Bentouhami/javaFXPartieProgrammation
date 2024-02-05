package be.bentouhami.reservotelapp.View;

import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.Model.BL.Hotel;
import be.bentouhami.reservotelapp.Model.BL.HotelList;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Supplier;


public class PrimaryView extends Application implements PropertyChangeListener, IView {
    private Pagination pagination;
    private final double txtF_prefWidth = 250;
    private final double txtF_prefHight = 50;
    private static Stage stage;
    private static Scene scene;
    private Controller control;
    private static GridPane gridPane;
    private static BorderPane borderPane;
    private Pane leftParent_vb;
    private MenuBar menuBar;


    @Override
    public void start(Stage primaryStage) throws SQLException {
        PrimaryView.stage = primaryStage;
        PrimaryView.stage.setOnCloseRequest(this.control.generateCloseEvent());
        showAcceuilView();
        stage.show();

    }

    @Override
    public void launchApp() {
        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                this.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public void stopApp() {

    }

    @Override
    public void setController(Controller controller) {
        this.control = controller;

    }


//    public void showHotelView(HotelList hotels) {
//        // Initialisation de base
//        borderPane.getChildren().clear();
//        borderPane = new BorderPane();
//
//        creatMenu();
//        gridPane = new GridPane();
//        //gridPane.setGridLinesVisible(true);
//        gridPane.setPrefSize(900, 600);
//        gridPane.setPadding(new Insets(20));
//        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//
//
//        int row = 0; // Compteur pour la position des éléments dans GridPane
//        if(hotels.isEmpty()){
//            this.showAcceuilView();
//        }
//        for (Hotel hotel : hotels) {
//            Label lbl = new Label(hotel.getDescrition() +
//                    ", \n" + hotel.getContactEmail() + "\n Prix Minimum  est de : " +
//                    "\n N° de Telephone: " +
//                    hotel.getContactTelephone() +", \n" +
//                    hotel.getPrixChambreMin() + "€");
//
//            // Pour chaque hôtel, créez et configurez les éléments nécessaires
//            ImageView hotelImageView = new ImageView();
//
//            String img_url = hotel.getPhoto();
//            //URL imageUrl = getClass().getResource(img_url);
//            if (img_url != null && !img_url.isEmpty()) {
//                Image image = new Image(img_url, true);
//                hotelImageView.setImage(image);
//            } else {
//                System.out.println(" lien null");
//            }
//            hotelImageView.setFitHeight(200);
//            hotelImageView.setFitWidth(200);
//
//            // Ajoutez l'image et la description de l'hôtel au GridPane
//            gridPane.add(lbl, 1, row);
//            gridPane.add(hotelImageView, 0, row);
////          gridPane.add(hotelDescription, 1, row);
//
//            row++;
//        }
//
//        // Configuration finale
//        borderPane.setTop(menuBar);
//        borderPane.setCenter(gridPane);
//        scene = new Scene(borderPane);
//        stage.setTitle("Hotels");
//        scene.getStylesheets().add(getClass().getResource("/be/bentouhami/reservotelapp/Styles/hotelsList.css").toExternalForm());
//        stage.setMinWidth(900);
//        stage.setMinHeight(600);
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
public void showHotelView(HotelList hotels) {
    // Initialisation de base
    borderPane.getChildren().clear();
    borderPane = new BorderPane();

    creatMenu();

    final int hotelsParPage = 6; // Nombre d'hôtels par page
    int pageCount = (int) Math.ceil((double) hotels.size() / hotelsParPage); // Calcul du nombre de pages



    pagination = new Pagination(pageCount, 0);
    pagination.setPageFactory(pageIndex -> {
        return createPage(pageIndex, hotels, hotelsParPage);
    });

    borderPane.setTop(menuBar);
    borderPane.setCenter(pagination); // Ajout de Pagination au centre du BorderPane

    scene = new Scene(borderPane);
    stage.setMinWidth(1028);
    stage.setMinHeight(800);
    stage.setTitle("Hotels");
    stage.setScene(scene);
    stage.show();
}

    @Override
    public void showChambresView(Hotel hotel) {
        gridPane = new GridPane();
        borderPane = new BorderPane();

    }

    private GridPane createPage(int pageIndex, HotelList hotels, int hotelsParPage) {
        GridPane gridPanePageContent = new GridPane();
        gridPanePageContent.setHgap(10); // Espace horizontal entre les éléments
        gridPanePageContent.setVgap(10); // Espace vertical entre les éléments


        int start = pageIndex * hotelsParPage;
        int end = Math.min(start + hotelsParPage, hotels.size());

        for (int i = start; i < end; i++) {
            Hotel hotel = hotels.get(i);
            Label lbl = new Label(hotel.getDescrition() +
                    ", \n" + hotel.getContactEmail() + "\n Prix Minimum est de : " +
                    "\n N° de Telephone: " +
                    hotel.getContactTelephone() + ", \n" +
                    hotel.getPrixChambreMin() + "€");

            ImageView hotelImageView = new ImageView();
            String img_url = hotel.getPhoto();
            if (img_url != null && !img_url.isEmpty()) {
                Image image = new Image(img_url, true);
                hotelImageView.setImage(image);
            }
            hotelImageView.setFitHeight(200); // Ajustez selon vos besoins
            hotelImageView.setFitWidth(200); // Ajustez selon vos besoins

            // Créer un HBox pour aligner l'image et le texte horizontalement
            HBox hotelInfoBox = new HBox(10); // Espace entre l'image et les détails
            hotelInfoBox.getChildren().addAll(hotelImageView, lbl);
            hotelInfoBox.setAlignment(Pos.CENTER_LEFT);

            int column = i % 2; // Alternance entre la colonne 0 et 1
            int row = i / 2; // Nouvelle ligne après chaque deux hôtels

            // Ajouter le HBox à la grille
            gridPanePageContent.setPrefSize(1028, 800);
            gridPanePageContent.setAlignment(Pos.CENTER);
            gridPanePageContent.add( hotelInfoBox, column, row);
        }

        return gridPanePageContent;
    }



//    private VBox createPage(int pageIndex, HotelList hotels, int hotelsParPage) {
//        VBox box = new VBox(5);
//        int start = pageIndex * hotelsParPage;
//        int end = Math.min(start + hotelsParPage, hotels.size());
//
//        for (int i = start; i < end; i++) {
//            Hotel hotel = hotels.get(i);
//            Label lbl = new Label(hotel.getDescrition() +
//                    ", \n" + hotel.getContactEmail() + "\n Prix Minimum est de : " +
//                    "\n N° de Telephone: " +
//                    hotel.getContactTelephone() +", \n" +
//                    hotel.getPrixChambreMin() + "€");
//
//            ImageView hotelImageView = new ImageView();
//            String img_url = hotel.getPhoto();
//            if (img_url != null && !img_url.isEmpty()) {
//                Image image = new Image(img_url, true);
//                hotelImageView.setImage(image);
//            }
//            hotelImageView.setFitHeight(200);
//            hotelImageView.setFitWidth(200);
//
//            // Configuration et ajout des éléments à box
//            HBox hBox = new HBox(hotelImageView, lbl); // Utiliser HBox pour aligner l'image et le texte
//            hBox.setSpacing(10); // Espace entre l'image et le texte
//            box.getChildren().add(hBox);
//        }
//        return box;
//    }


    @Override
    public void showProfilView(String nom,
                               String prenom,
                               String dateNaissance,
                               String numTelephone,
                               String email,
                               int pointsFidelite,
                               String password,
                               String rue,
                               String numRue,
                               String boite,
                               String codepostal,
                               String ville,
                               String pays) {

        // Ajouter le Menu
        creatMenu();

        // Ajouter left side

        leftParent_vb = new VBox();


        // Preparer gridPane et BorderPane
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        //gridPane.setGridLinesVisible(true);
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(gridPane);
        borderPane.setLeft(leftParent_vb);

        // setton posations
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.setPrefSize(800, 600);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);

// Création et ajout des labels
        Label lblNom = new Label("Nom");
        Label lblPrenom = new Label("Prénom");
        Label lblDateNaissance = new Label("Date de naissance");
        Label lblEmail = new Label("E-mail");
        Label lblNumTelephone = new Label("Numéro de telephone");
        Label lblPointsFidelite = new Label("Points de fidelite");
        Label lblOldPassword = new Label("Ancian mot de passe");
        Label lblNewPassword = new Label("Nouveau mot de passe");
        Label lblRue = new Label("Rue");
        Label lblNumRue = new Label("Numéro");
        Label lblBoite = new Label("Boite");
        Label lblVille = new Label("Ville");
        Label lblCodepostal = new Label("Codepostal");
        Label lblPays = new Label("Pays");


        // Création et ajout des champs de texte pour les informations de l'utilisateur
        TextField txtNom = new TextField(nom);
        TextField txtPrenom = new TextField(prenom);
        TextField txtDatePickerNaissance = new TextField(dateNaissance); // Pour la date de naissance
        TextField txtNumTelephone = new TextField(numTelephone);
        TextField txtEmail = new TextField(email);
        TextField txtPointsFidelite = new TextField(Integer.toString(pointsFidelite));


        // Création et ajout des champs pour l'adresse
        TextField txtRue = new TextField(rue);
        TextField txtNumRue = new TextField(numRue);
        TextField txtBoite = new TextField(boite);
        TextField txtVille = new TextField(ville);
        TextField txtCodepostal = new TextField(codepostal);
        TextField txtPays = new TextField(pays);

        // Création et ajout des champs pour le changement de mot de passe
        PasswordField pwdAncien = new PasswordField();
        pwdAncien.setPromptText("Ancien mot de passe");
        PasswordField pwdNouveau = new PasswordField();
        pwdNouveau.setPromptText("Nouveau mot de passe");

        // disactiver les champs non changables
        txtDatePickerNaissance.setDisable(true);
        setDisableTxtF(txtNom,
                txtPrenom,
                txtDatePickerNaissance,
                txtPointsFidelite);

        // Ajouter les dimensions pour les TextFields
        setTxtPrefSize(txtNom,
                txtPrenom,
                txtDatePickerNaissance,
                txtNumTelephone,
                txtEmail,
                txtPointsFidelite,
                pwdAncien,
                pwdNouveau,
                txtRue,
                txtNumRue,
                txtBoite,
                txtVille,
                txtCodepostal,
                txtPays);


        // Création et configuration du bouton de sauvegarde
        Button btnSave = new Button("Sauvegarder");
        btnSave.setOnAction(event -> {
            // Logique de sauvegarde
        });

        // Ajout des éléments au GridPane
        // Ajouter chaps de client
        gridPane.add(lblNom, 0, 0);
        gridPane.add(txtNom, 1, 0);
        gridPane.add(lblPrenom, 0, 1);
        gridPane.add(txtPrenom, 1, 1);
        gridPane.add(lblDateNaissance, 0, 2);
        gridPane.add(txtDatePickerNaissance, 1, 2);
        gridPane.add(lblNumTelephone, 0, 3);
        gridPane.add(txtNumTelephone, 1, 3);
        gridPane.add(lblEmail, 0, 4);
        gridPane.add(txtEmail, 1, 4);
        gridPane.add(lblPointsFidelite, 0, 5);
        gridPane.add(txtPointsFidelite, 1, 5);
        gridPane.add(lblOldPassword, 0, 6);
        gridPane.add(pwdAncien, 1, 6);
        gridPane.add(lblNewPassword, 0, 7);
        gridPane.add(pwdNouveau, 1, 7);



        // Ajouter les champs de Adresse
        gridPane.add(lblRue, 3, 0);
        gridPane.add(txtRue, 4, 0);
        gridPane.add(lblNumRue, 3, 1);
        gridPane.add(txtNumRue, 4, 1);
        gridPane.add(lblBoite, 3, 2);
        gridPane.add(txtBoite, 4, 2);
        gridPane.add(lblVille, 3, 3);
        gridPane.add(txtVille, 4, 3);
        gridPane.add(lblCodepostal, 3, 4);
        gridPane.add(txtCodepostal, 4, 4);
        gridPane.add(lblPays, 3, 5);
        gridPane.add(txtPays, 4, 5);

        //  Ajouter la button sauvegarder dans notre GridPane

        btnSave.getStyleClass().add("search-btn");
        gridPane.add(btnSave, 4, 9);


        // Ajouter les delatils fix de client

        Label lblFullNameClientLeft = new Label("Client: "+nom.trim() + " " + prenom.trim());
        Label lblEmailClientLeft = new Label("Email: " + email.trim());
        Label lblFideliteLeft = new Label("Points de fidelite: " + Integer.toString(pointsFidelite).trim());
        lblFullNameClientLeft.getStyleClass().addAll("hotel_container",
                "FontAwesomeIconView",
                "hotel_logo_container");
        Color paint = new Color( 0.93, 0.94, 0.83, 1.0);

        Font font = Font.font("Arial", FontWeight.findByName("Bold"),20);

        lblFullNameClientLeft.setFont(font);
        lblFullNameClientLeft.setTextFill(paint);

        lblEmailClientLeft.setStyle("-fx-font-size: 15; -fx-text-fill: WHITE; -fx-alignment: CENTER; -fx-font-family: 'Verdana Pro';");
        lblFideliteLeft.setStyle("-fx-font-size: 15; -fx-text-fill: WHITE; -fx-alignment: CENTER; -fx-font-family: 'Verdana Pro';");
        leftParent_vb.getChildren().addAll(lblFullNameClientLeft, lblEmailClientLeft, lblFideliteLeft);
        leftParent_vb.getStyleClass().add("hotel_logo_container");

        Supplier <String[]> supplier = () -> new  String[]{txtNumTelephone.getText(),
                txtEmail.getText(),
                pwdAncien.getText(),
                pwdNouveau.getText(),
                txtRue.getText(),
                txtNumRue.getText(),
                txtBoite.getText(),
                txtCodepostal.getText(),
                txtVille.getText(),
                txtPays.getText()
        };
        btnSave.setOnAction(control.generateEventHandlerAction("showProfil", supplier));


        // Set VBox to the left of BorderPane
        leftParent_vb.setPrefWidth(300);
        leftParent_vb.setPadding(new Insets(10, 10, 10 , 0));
        borderPane.setLeft(leftParent_vb);

        scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/be/bentouhami/reservotelapp/Styles/stylesheet.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle(nom + " " + prenom + "| Profil");
        stage.setMinWidth(1028);
        stage.setMinHeight(500);
        stage.show();


    }// end methode

    private void setTxtPrefSize(TextField ... txtFs) {
        for (TextField txtF: txtFs) {
            txtF.setPrefSize(txtF_prefWidth, txtF_prefHight);
        }
    }

    private void setDisableTxtF(TextField... txtFs) {

        for (TextField txt: txtFs) {
            txt.setDisable(true);
        }

    }

    @Override
    public void showLoginView() {

        // setting up containers
        borderPane = new BorderPane();
        gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);
        leftParent_vb = new VBox();
        Label lbl_error = new Label();
        String error_default_style = "-fx-text-fill: #ff0000; -fx-font-size: 10; -fx-padding: 5;";
        lbl_error.setStyle(error_default_style);

        String textField_default_style = "-fx-padding: 5; -fx-font-size: 20; -fx-pref-width: 300; -fx-pref-height: 45";
        String pwdField_default_style = "-fx-padding: 5; -fx-font-size: 20; -fx-pref-width: 300; -fx-pref-height: 45";

        // setting up formule fields and buttons
        // identification
        TextField txtf_identifiant = new TextField();
        txtf_identifiant.setPromptText("Entrer votre Identifiant");
        txtf_identifiant.setStyle(textField_default_style);


        HBox identifianBox = new HBox(txtf_identifiant);


        // password
        PasswordField pwdf_password = new PasswordField();

        identifianBox.setAlignment(Pos.CENTER);

        pwdf_password.setPromptText("Entrer votre mode de passe");
        pwdf_password.setStyle(pwdField_default_style);

        HBox passwordBox = new HBox(pwdf_password);
        passwordBox.setSpacing(5); // Ajustez l'espace entre l'icône et le champ de mot de passe
        passwordBox.setAlignment(Pos.CENTER);


        // buttons
        Button btn_connecte = new Button("Se Connecter");

        Button btn_inscription = new Button("S'inscrire");

        FontAwesomeIconView btn_connect_icon = new FontAwesomeIconView(FontAwesomeIcon.SIGN_IN);
        FontAwesomeIconView btn_inscription_icon = new FontAwesomeIconView(FontAwesomeIcon.USER_PLUS);

        btn_connecte.getStyleClass().add("search-btn");
        btn_connecte.setGraphic(btn_connect_icon);
        btn_inscription.getStyleClass().add("search-btn");
        btn_inscription.setGraphic(btn_inscription_icon);

        btn_connecte.setPrefSize(200, 30);
        btn_inscription.setPrefSize(200, 30);


        // setting up controls
        Label lbl_title = new Label("Reservotel");
        Label lbl_sous_title = new Label("Reserver en 1 Clic");

        FontAwesomeIconView lbl_clic_font_icon = new FontAwesomeIconView();
        lbl_clic_font_icon.setGlyphName("CHECK");
        lbl_clic_font_icon.setSize("2em");

        // style labels
        lbl_title.setTextFill(Color.WHITE);
        lbl_title.setStyle("-fx-font-size: 50; -fx-alignment: CENTER; -fx-font-family: 'Verdana Pro';");
        lbl_sous_title.setTextFill(Color.WHITE);
        lbl_sous_title.setStyle("-fx-font-size: 20; -fx-font-family: 'Verdana Pro';");
        // logo image
        Image logo_img = new Image(getClass().getResource("/images/Reservotel.png").toExternalForm());
        ImageView logo_imageView = new ImageView(logo_img);

        // setting app logo size
        logo_imageView.setFitWidth(150);
        logo_imageView.setFitHeight(150);
        HBox hb_sous_titl_icon = new HBox(5);
        hb_sous_titl_icon.setAlignment(Pos.CENTER);
        hb_sous_titl_icon.getChildren().addAll(lbl_clic_font_icon, lbl_sous_title);

        leftParent_vb.getChildren().addAll(logo_imageView, lbl_title, hb_sous_titl_icon);

        leftParent_vb.getStyleClass().add("hotel_logo_container");

        // Set VBox to the left of BorderPane
        leftParent_vb.setPrefWidth(300);
        borderPane.setLeft(leftParent_vb);

        // positioning controls in gridPane


        gridPane.add(identifianBox, 0, 2, 4, 1);
        gridPane.add(passwordBox, 0, 3, 4, 1);
        gridPane.add(lbl_error, 0, 4, 4, 1);
        gridPane.add(btn_connecte, 1, 5, 4, 1);
        gridPane.add(btn_inscription, 1, 6, 4, 1);

        GridPane.setHalignment(btn_connecte, HPos.CENTER);
        GridPane.setValignment(btn_connecte, VPos.CENTER);
        GridPane.setHalignment(btn_inscription, HPos.CENTER);
        GridPane.setValignment(btn_inscription, VPos.CENTER);

        gridPane.setAlignment(Pos.CENTER);
        // setting up borderPane
        borderPane.setCenter(gridPane);


        gridPane.setVgap(10); // Ajoute un espace vertical de 10 pixels entre les lignes
        gridPane.setHgap(10); // Ajoute un espace horizontal de 10 pixels entre les colonnes

        String redBorderStyle = textField_default_style + "; -fx-border-color: red; -fx-border-width: 2px;";

        Supplier<String[]> supplier = () -> new String[]{" "};
        boolean isIdEmpty = txtf_identifiant.getText().isEmpty();
        boolean isPasswordEmpty = pwdf_password.getText().isEmpty();


        supplier = () -> new String[]{txtf_identifiant.getText(), pwdf_password.getText()};
        btn_connecte.setOnAction(this.control.generateEventHandlerAction("checkClientData", supplier));


        supplier = () -> new String[]{""};
        btn_inscription.setOnAction(this.control.generateEventHandlerAction("showInscriptionView", supplier));

        txtf_identifiant.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                txtf_identifiant.setStyle(redBorderStyle); // Style rouge si vide
                lbl_error.setText("L'identifiant ne peut pas être vide.");
            } else {
                txtf_identifiant.setStyle(textField_default_style); // Style par défaut si non vide
                lbl_error.setText(""); // Effacer le message d'erreur
            }
        });

        pwdf_password.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                pwdf_password.setStyle(redBorderStyle); // Style rouge si vide
                lbl_error.setText("Le mot de passe ne peut pas être vide.");
            } else {
                pwdf_password.setStyle(pwdField_default_style); // Style par défaut si non vide
                lbl_error.setText(""); // Effacer le message d'erreur
            }
        });


        stage.setTitle("Login | Inscription");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/be/bentouhami/reservotelapp/Styles/stylesheet.css").toExternalForm());

        stage.setScene(scene);

        stage.show();
        // Obtenir les dimensions de l'écran
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);

    }

    @Override
    public void showAcceuilView() {

        borderPane = new BorderPane();
        gridPane = new GridPane();
        leftParent_vb = new VBox();
        // create the Menu and add it to the BorderPane (top)
        creatMenu();
        // prepare VBox to the left
        Text title = new Text("Reservotel");
        title.getStyleClass().addAll("hotel_container",
                "FontAwesomeIconView",
                "hotel_logo_container");

        //title.setFont(Font.font("Verdana", FontWeight.BOLD,24));

        FontAwesomeIconView hotelFontIcon = new FontAwesomeIconView();

        hotelFontIcon.setGlyphName("HOTEL");
        hotelFontIcon.setSize("10em");

        Label hotelIcon_lb = new Label("", hotelFontIcon);
        hotelIcon_lb.getStyleClass().addAll("hotel_container",
                "FontAwesomeIconView",
                "hotel_logo_container");

        // add logo to grid
        Image logo_img = new Image(getClass().getResource("/images/Reservotel.png").toExternalForm());
        ImageView logo_imageView = new ImageView(logo_img);


        logo_imageView.setFitWidth(200);
        logo_imageView.setFitHeight(200);

        leftParent_vb.getChildren().addAll(title, hotelIcon_lb);
        leftParent_vb.getStyleClass().add("hotel_logo_container");

        // Set VBox to the left of BorderPane
        leftParent_vb.setPrefWidth(300);
        borderPane.setLeft(leftParent_vb);

        Label lblPays = new Label("Pays");

        // set search labels and text fields
        Label ville_lbl = new Label("Ville");
        ville_lbl.getStyleClass().add("search-fields-labels");

        TextField ville_txtf = new TextField();
        ville_txtf.setPrefSize(250, 50);
        ville_txtf.getStyleClass().add("ville-txtf");

        // set dates

        // date arrive
        Label dateArrive_lbl = new Label("Date Arrive");
        //dateArrive_lbl.getStyleClass().add("search-fields-labels");

        DatePicker dateArrive_dtp = new DatePicker(LocalDate.now());
        dateArrive_dtp.setPrefSize(250, 50);

        // date de depart
        Label dateDepart_lbl = new Label("Date Depart");
        DatePicker dateDepart_dtp = new DatePicker(LocalDate.now());
        dateDepart_dtp.setPrefSize(250, 50);


        // set nombre de personne
        Label nombrePersonne_lbl = new Label("Nombre de personnes");
        TextField nombrePersonne_txtf = new TextField();

        nombrePersonne_txtf.setPrefSize(250, 50);
        nombrePersonne_txtf.setPadding(new Insets(0, 0, 0, 0));
        //gridPane.setGridLinesVisible(true);
        // set Botton search
        Button search_btn = new Button("Search");


        //search_btn.getStyleClass().add("/be/bentouhami/reservotelapp/Images/ReservotelLogo.9.png");
        search_btn.getStyleClass().add("search-btn");

        // add controls to gridPane
        //gridPane.setGridLinesVisible(true);
        gridPane.add(logo_imageView, 2, 0, 6, 1);
        gridPane.add(ville_lbl, 0, 2);
        gridPane.add(ville_txtf, 3, 2, 2, 1);
        gridPane.add(dateArrive_lbl, 0, 3);
        gridPane.add(dateArrive_dtp, 3, 3, 3, 1);
        gridPane.add(dateDepart_lbl, 0, 4);
        gridPane.add(dateDepart_dtp, 3, 4, 3, 1);
        gridPane.add(nombrePersonne_lbl, 0, 5);
        gridPane.add(nombrePersonne_txtf, 3, 5, 2, 1);
        gridPane.add(search_btn, 4, 6, 3, 2);


        // setting up positions
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.setPrefSize(900, 600);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);

        // Set BorderPane as the root of the scene
        scene = new Scene(borderPane);
        stage.setTitle("Reservotel");
        scene.getStylesheets().add(getClass().getResource("/be/bentouhami/reservotelapp/Styles/stylesheet.css").toExternalForm());


        // set up supplier
        Supplier<String[]> supplier = () -> new String[]{
                ville_txtf.getText(),
                dateArrive_dtp.getValue().toString(),
                dateDepart_dtp.getValue().toString(),
                nombrePersonne_txtf.getText()
        };
        search_btn.setOnAction(control.generateEventHandlerAction("show-hotels", supplier));


        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();

    }


    private void creatMenu() {
        // prepare MenuBar & menu items
        Menu menu = new Menu("Client");
        MenuItem loginMenu = new MenuItem("Login");
        MenuItem logoutMenu = new MenuItem("Logout");
        MenuItem profilMenu = new MenuItem("Profil");

        // add menu items to the Menu
        menu.getItems().addAll(loginMenu, logoutMenu, profilMenu);
        // prepare MenuBar
        menuBar = new MenuBar();
        // add Menu to MenuBar
        menuBar.getMenus().addAll(menu);

        Supplier<String[]> supplier = () -> new String[]{" "};
        loginMenu.setOnAction(control.generateEventHandlerAction("showLoginView", supplier));
        supplier = () -> new String[]{" "};
        logoutMenu.setOnAction(control.generateEventHandlerAction("logout", supplier));
        supplier = () -> new String[]{" "};
        profilMenu.setOnAction(control.generateEventHandlerAction("showProfilView", supplier));


        borderPane.setTop(menuBar);


    }// creatMenu


    @Override
    public void showInscription() {
        creatMenu();
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        //gridPane.setGridLinesVisible(true);
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(gridPane);

        // setton posations
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.setPrefSize(900, 600);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);

        //setting up left controls
        Label lbl_nom = new Label("Nom");
        Label lbl_prenom = new Label("Prenom");
        Label lbl_date_naissance = new Label("Date de naissance");
        Label lbl_numero_tel = new Label("Numéro de telephone");
        Label lbl_email = new Label("Email");
        Label lbl_new_password = new Label("Nouveau mot de passe");
        Label lbl_verify_password = new Label("Verifier mot de passe");

        //setting up right controls
        Label lbl_rue = new Label("Rue");
        Label lbl_numRue = new Label("Numero");
        Label lbl_boite = new Label("Boite");
        Label lbl_codePostal = new Label("Code postal");
        Label lbl_ville = new Label("Ville");
        Label lbl_pays = new Label("Pays");

        // setting up left inputs
        TextField txtF_nom = new TextField();
        TextField txtF_prenom = new TextField();
        DatePicker dteP_date_naissance = new DatePicker(LocalDate.now());
        TextField txtF_num_tel = new TextField();
        TextField txtF_email = new TextField();
        PasswordField pwrdTxtF_password = new PasswordField();
        PasswordField pwrdTxtF_verifyPassword = new PasswordField();

        // setting up right inputs
        TextField txtF_rue = new TextField();
        TextField txtF_numRue = new TextField();
        TextField txtF_boite = new TextField();
        TextField txtF_code_postal = new TextField();
        TextField txtF_ville = new TextField();
        TextField txtF_pays = new TextField();


        Button createClient = new Button("Creer le compte");

        createClient.getStyleClass().add("search-btn");

        //  setting up left labels  in the gride pane
        gridPane.add(lbl_nom, 0, 0, 1, 1);
        gridPane.add(lbl_prenom, 0, 1, 1, 1);
        gridPane.add(lbl_date_naissance, 0, 2, 1, 1);
        gridPane.add(lbl_numero_tel, 0, 3, 1, 1);
        gridPane.add(lbl_email, 0, 4, 1, 1);
        gridPane.add(lbl_new_password, 0, 5, 1, 1);
        gridPane.add(lbl_verify_password, 0, 6, 1, 1);


        //  setting up left inputs grid pane right
        gridPane.add(txtF_nom, 2, 0, 1, 1);
        gridPane.add(txtF_prenom, 2, 1, 1, 1);
        gridPane.add(dteP_date_naissance, 2, 2, 1, 1);
        gridPane.add(txtF_num_tel, 2, 3, 1, 1);
        gridPane.add(txtF_email, 2, 4, 1, 1);
        gridPane.add(pwrdTxtF_password, 2, 5, 1, 1);
        gridPane.add(pwrdTxtF_verifyPassword, 2, 6, 1, 1);

        //  setting up right labels  in the gride pane
        gridPane.add(lbl_rue, 4, 0, 1, 1);
        gridPane.add(lbl_numRue, 4, 1, 1, 1);
        gridPane.add(lbl_boite, 4, 2, 1, 1);
        gridPane.add(lbl_codePostal, 4, 3, 1, 1);
        gridPane.add(lbl_ville, 4, 4, 1, 1);
        gridPane.add(lbl_pays, 4, 5, 1, 1);

        //  setting up right inputs grid pane right
        gridPane.add(txtF_rue, 5, 0, 1, 1);
        gridPane.add(txtF_numRue, 5, 1, 1, 1);
        gridPane.add(txtF_boite, 5, 2, 1, 1);
        gridPane.add(txtF_code_postal, 5, 3, 1, 1);
        gridPane.add(txtF_ville, 5, 4, 1, 1);
        gridPane.add(txtF_pays, 5, 5, 1, 1);

        gridPane.add(createClient, 3, 9, 1, 1);

        Supplier<String[]> supplier = () -> new String[]{
                txtF_nom.getText().trim(),
                txtF_prenom.getText().trim(),
                dteP_date_naissance.getValue().toString().trim(),
                txtF_num_tel.getText().trim(),
                txtF_email.getText().trim(),
                pwrdTxtF_password.getText().trim(),
                pwrdTxtF_verifyPassword.getText().trim(),
                txtF_rue.getText().trim(),
                txtF_numRue.getText().trim(),
                txtF_boite.getText().trim(),
                txtF_code_postal.getText().trim(),
                txtF_ville.getText().trim(),
                txtF_pays.getText().trim(),

        };

        createClient.setOnAction(control.generateEventHandlerAction("addNewClientWithAdresse", supplier));


        scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/be/bentouhami/reservotelapp/Styles/stylesheet.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Inscription");
        stage.setMinWidth(900);
        stage.setMinHeight(400);
        stage.show();

    }


    @Override
    public void showAlert(Alert.AlertType alertType, String message, ButtonType btn) {
        Alert alert = new Alert(alertType, message, btn);
        alert.showAndWait();

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case "hotelsList":
                if (evt.getNewValue().getClass().isAssignableFrom(HotelList.class))
                    this.showHotelView((HotelList) evt.getNewValue());
                break;
            case "login":
                if (evt.getNewValue().getClass().isAssignableFrom(ArrayList.class))
                    this.showLoginView();
                break;
//            case "showProfil":
//                if (evt.getNewValue().getClass().isAssignableFrom(ArrayList.class))
//                    this.showLoginView();
//                break;

            default:
                break;
        }

    }

}
