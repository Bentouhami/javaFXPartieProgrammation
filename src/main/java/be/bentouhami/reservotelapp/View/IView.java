package be.bentouhami.reservotelapp.View;


import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.Model.BL.ChambreList;
import be.bentouhami.reservotelapp.Model.BL.HotelList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public interface IView {
    void showProfilView(ArrayList<String> clientConnectedDatas);

    void showUpdatePassword();

    void showLoginView();
    void showHotelView(HotelList hotels);

    void showChambresView(ChambreList chambres);

    void showAcceuilView();
   // void showConnexionView();
    void stopApp();
    void setController(Controller controller);

    void launchApp();

   void showInscription();

   //void showChambresChoices();

    void showAlert(Alert.AlertType error, String s, ButtonType ok);



}
