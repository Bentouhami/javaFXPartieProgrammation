package be.bentouhami.reservotelapp;


import be.bentouhami.reservotelapp.Controller.Controller;
import be.bentouhami.reservotelapp.DataSource.DataInitializer;

import java.io.IOException;
import java.sql.SQLException;

import static be.bentouhami.reservotelapp.DataSource.CreateDB.initializeDatabase;
import static be.bentouhami.reservotelapp.DataSource.CreateDBTables.createTables;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        initializeDatabase();
        createTables();
        DataInitializer dataInitializer = new DataInitializer();
        Controller controller = new Controller();
        controller.initialize();
        controller.start();
    }
}