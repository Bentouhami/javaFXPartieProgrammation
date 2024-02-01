package be.bentouhami.reservotelapp.View;


import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.Model.BL.Client;
import be.bentouhami.reservotelapp.Model.BL.DetailsReservationList;
import be.bentouhami.reservotelapp.Model.BL.HotelList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public interface IView {

    void showProfilView(Client client);
    void showLoginView();
    void showHotelView(HotelList hotels);
    void showAcceuilView();
    void showConnexionView();
    void showCreationCompteView();
    void showRecapView(DetailsReservationList detailsReservations);
    void stopApp();
    void setController(Controller controller);

    void launchApp();

    void showValidReservation(ArrayList<String> reservation);

    void showInscription();

    void showAlert(Alert.AlertType error, String s, ButtonType ok);
    void showProfil(Client c);
}
