package be.bentouhami.reservotelapp.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FXGUI {
    private final double monPrefWidth = 250;
    private final double monPrefHight = 50;

    public FXGUI() {
        super();
    }

    public void setEquipemetsStyle(Label lblEq, Label lblEqDescription, Color color) {
        lblEq.setTextFill(color);
        lblEqDescription.setTextFill(color);
        lblEq.setPadding(new Insets(5));
        lblEq.setAlignment(Pos.TOP_LEFT);
        lblEqDescription.setMaxWidth(255);
        lblEqDescription.setPadding(new Insets(0, 10, 0, 5));
        lblEqDescription.setAlignment(Pos.TOP_LEFT);
        lblEqDescription.setWrapText(true);
    }

    public void setTxtPrefSize(TextField... txtFs) {
        for (TextField txtF : txtFs) {
            txtF.setPrefSize(monPrefWidth, monPrefHight);
        }
    }

    public void setPrefSize(ComboBox... boxs) {
        for (ComboBox box : boxs) {
            box.setPrefSize(monPrefWidth, monPrefHight);
        }
    }
    public void setPrefSize(DatePicker... dates) {
        for (DatePicker date : dates) {
            date.setPrefSize(monPrefWidth, monPrefHight);
        }
    }

    public void setLabelLinks(Label ... lbls){
        // handel Mouse
        // Style par défaut
        String styleDefault = "-fx-text-fill: WHITE; -fx-font-size: 1em; -fx-padding: 5";
        // Style au survol
        String styleHover = "-fx-text-fill: LIGHTBLUE; -fx-font-size: 1em; -fx-padding: 5";

        for (Label lbl : lbls) {
            // Appliquer le style par défaut
            lbl.setStyle(styleDefault);
            // Changer le curseur en main au survol
            lbl.setOnMouseEntered((MouseEvent e) -> {
                lbl.setStyle(styleHover);
                lbl.setCursor(Cursor.HAND);
            });

            lbl.setOnMouseExited(e -> lbl.setCursor(Cursor.DEFAULT));
            // Gestion des événements de survol pour changer la couleur comme un lien
            lbl.setOnMouseExited(e -> lbl.setStyle(styleDefault));
        }
    }

    public void setLeftLblStyles(Label... labels) {
        for (Label lbl : labels) {
            lbl.setStyle("-fx-padding: 10;" + " -fx-font-size: 2em; " + "-fx-text-fill: YELLOW;" + " -fx-alignment: CENTER;" + "-fx-cursor: HAND");
        }
    }

    public void setDisableTxtF(TextField... txtFs) {

        for (TextField txt : txtFs) {
            txt.setDisable(true);
        }
    }

}
