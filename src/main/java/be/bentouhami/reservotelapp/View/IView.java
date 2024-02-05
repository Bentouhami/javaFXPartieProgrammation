package be.bentouhami.reservotelapp.View;


import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.Model.BL.Hotel;
import be.bentouhami.reservotelapp.Model.BL.HotelList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public interface IView {
    void showProfilView(String nom,
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
                        String pays);

    void showLoginView();
    void showHotelView(HotelList hotels);
    void showChambresView(Hotel hotel);
    void showAcceuilView();
   // void showConnexionView();
    void stopApp();
    void setController(Controller controller);

    void launchApp();

   void showInscription();

    void showAlert(Alert.AlertType error, String s, ButtonType ok);



}
