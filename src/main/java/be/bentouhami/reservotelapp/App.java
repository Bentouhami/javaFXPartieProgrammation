package be.bentouhami.reservotelapp;


import be.bentouhami.reservotelapp.Controller.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        Controller controller = new Controller();
        controller.initialize();
        controller.start();


    }
}