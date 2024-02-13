module be.bentouhami.reservotelapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires org.kordamp.bootstrapfx.core;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires jbcrypt;


    opens be.bentouhami.reservotelapp to javafx.fxml;
    opens be.bentouhami.reservotelapp.Controller to javafx.fxml;

    exports be.bentouhami.reservotelapp;
    exports be.bentouhami.reservotelapp.View;
    exports be.bentouhami.reservotelapp.Model;
    exports be.bentouhami.reservotelapp.Model.BL;
    exports be.bentouhami.reservotelapp.Controller;



}