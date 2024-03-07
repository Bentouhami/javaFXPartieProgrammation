package be.bentouhami.reservotelapp.View;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public void getRetourBtn(Button btn_back) {
        btn_back.getStyleClass().add("search-btn");
        FontAwesomeIconView retour_icon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        btn_back.setGraphic(retour_icon);
    }

    public void getReservationBtn(Button btnAjouterRes) {
        btnAjouterRes.getStyleClass().add("search-btn");
        FontAwesomeIconView retour_icon = new FontAwesomeIconView(FontAwesomeIcon.SHOPPING_BASKET);
        btnAjouterRes.setGraphic(retour_icon);
    }


    public ImageView getImageView(String imageUrl, double width, double height) {
        ImageView imageView = new ImageView();

        // Vérification si l'URL de l'image n'est non nulle et non vide.
        // Si oui, crée une nouvelle image à partir de l'URL et l'associe à l'ImageView pour l'afficher.
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Image image = new Image(imageUrl, true); // Le second argument 'true' signifie que l'image sera chargée en arrière-plan.
            imageView.setImage(image); // Association l'image chargée à l'ImageView.
        }

        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
}
