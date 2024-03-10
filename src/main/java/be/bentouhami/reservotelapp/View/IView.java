package be.bentouhami.reservotelapp.View;


import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.Model.BL.Containers.ChambreDatas;
import be.bentouhami.reservotelapp.Model.BL.Containers.ContainerLists;
import be.bentouhami.reservotelapp.Model.BL.Reservation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public interface IView {
    void showChambresView(ArrayList<String[]> chambres);

    void showProfilView(ArrayList<String> clientConnectedDatas);

    void showUpdatePassword();

    void showLoginView();

    void showHotelView(ContainerLists containerLists);

    void showAlertNombrePersonnesRestantes(Integer nombrePersonnesRestantes);

    void showOptionsView(ArrayList<String[]> options);

    void showAcceuilView();

    void stopApp();

    void setController(Controller controller);

    void launchApp();

    void showInscription();

    void showAlert(Alert.AlertType error, String s, ButtonType ok);

    void logout(ArrayList<String> connectedClient);
    void showChambreAndOptions(ChambreDatas chambreData);

    boolean showAddNewChambre(Button btn_ajouterRes, String s);

    void showChambresList();

    void showReservationRecap(Reservation newValue);
    void showAllReservations(ArrayList<Reservation> myReservations);

}