package be.bentouhami.reservotelapp.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

}
