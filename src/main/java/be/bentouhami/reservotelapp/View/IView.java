package be.bentouhami.reservotelapp.View;


import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.Model.BL.ChambreList;
import be.bentouhami.reservotelapp.Model.BL.Containers.ChambreDatas;
import be.bentouhami.reservotelapp.Model.BL.Containers.ContainerLists;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public interface IView {
    void showProfilView(ArrayList<String> clientConnectedDatas);

    void showUpdatePassword();

    void showLoginView();

    void showHotelView(ContainerLists containerLists);

    void showChambresView(ChambreList chambres);
    void showOptionsView(ArrayList<String[]> options);

    void showAcceuilView();

    void stopApp();

    void setController(Controller controller);

    void launchApp();

    void showInscription();

    void showAlert(Alert.AlertType error, String s, ButtonType ok);

    void logout(ArrayList<String> connectedClient);
    void showChambreAndOptions(ChambreDatas chambreData);
}
