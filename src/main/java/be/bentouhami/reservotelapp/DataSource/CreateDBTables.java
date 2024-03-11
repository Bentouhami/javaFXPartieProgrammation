package be.bentouhami.reservotelapp.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDBTables {
    public static void createTables() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (Statement statement = conn.createStatement()) {
            // Création de la table adresses
            String sqlCreateAdresses = """
                    CREATE TABLE IF NOT EXISTS adresses (
                        id_adresse SERIAL PRIMARY KEY,
                        rue VARCHAR(200) NOT NULL,
                        numero VARCHAR(10) NOT NULL,
                        boite VARCHAR(10),
                        ville VARCHAR(100) NOT NULL,
                        codepostal VARCHAR(20) NOT NULL,
                        pays VARCHAR(100) NOT NULL
                    );""";

            // Création de la table options
            String sqlCreateOptions = """
                    CREATE TABLE IF NOT EXISTS options (
                        id_option SERIAL PRIMARY KEY,
                        description_option VARCHAR(255) NOT NULL,
                        option VARCHAR(255) NOT NULL
                    );""";

            // Création de la table equipements
            String sqlCreateEquipements = """
                    CREATE TABLE IF NOT EXISTS equipements (
                        id_equipement SERIAL PRIMARY KEY,
                        equipement VARCHAR(100) NOT NULL,
                        description_equipement TEXT NOT NULL
                    );""";

            // Hotels
            String sqlCreateHotels = """
                    CREATE TABLE IF NOT EXISTS hotels (
                        id_hotel SERIAL PRIMARY KEY,
                        adresse_id INTEGER NOT NULL,
                        nom_hotel VARCHAR(200) NOT NULL UNIQUE,
                        etoiles INTEGER NOT NULL CHECK (etoiles >= 1 AND etoiles <= 5),
                        description_hotel TEXT,
                        photo_hotel TEXT,
                        prix_chambre_min NUMERIC(10, 2) NOT NULL,
                        nombre_chambre INTEGER NOT NULL,
                        contact_telephone VARCHAR(200) UNIQUE,
                        contact_email VARCHAR(200) NOT NULL UNIQUE,
                        CONSTRAINT fk_hotel_adresse FOREIGN KEY(adresse_id) REFERENCES adresses(id_adresse)
                    );""";

            // Clients
            String sqlCreateClients = """
                    CREATE TABLE IF NOT EXISTS clients (
                        id_client SERIAL PRIMARY KEY,
                        adresse_id INTEGER NOT NULL,
                        nom_client VARCHAR(100) NOT NULL,
                        prenom VARCHAR(100) NOT NULL,
                        date_naissance DATE NOT NULL,
                        email_client VARCHAR(250) NOT NULL UNIQUE,
                        num_telephone VARCHAR(20) NOT NULL UNIQUE,
                        points_fidelite INTEGER DEFAULT 0 NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        CONSTRAINT fk_client_adresse FOREIGN KEY(adresse_id) REFERENCES adresses(id_adresse)
                    );""";

            // Chambres
            String sqlCreateChambres = """
                    CREATE TABLE IF NOT EXISTS chambres (
                        id_chambre SERIAL PRIMARY KEY,
                        hotel_id INTEGER NOT NULL,
                        numero_chambre VARCHAR(10) NOT NULL,
                        etage INTEGER NOT NULL,
                        nombre_personnes INTEGER NOT NULL,
                        est_disponible BOOLEAN NOT NULL,
                        photo_chambre TEXT,
                        type_chambre VARCHAR(100) NOT NULL,
                        lits VARCHAR(100) NOT NULL,
                        prix_chambre NUMERIC(10, 2) NOT NULL,
                        CONSTRAINT fk_chambre_hotel FOREIGN KEY(hotel_id) REFERENCES hotels(id_hotel)
                    );""";

            // Reservations
            String sqlCreateReservations = """
                    CREATE TABLE IF NOT EXISTS reservations (
                        id_reservation SERIAL PRIMARY KEY,
                        client_id INTEGER NOT NULL,
                        statut_reservation VARCHAR(50),
                        date_arrive DATE,
                        date_depart DATE,
                        prix_total_reservation NUMERIC(10, 2),
                        date_creation TIMESTAMP DEFAULT NOW(),
                        date_modification TIMESTAMP,
                        CONSTRAINT fk_reservation_client FOREIGN KEY(client_id) REFERENCES clients(id_client)
                    );""";

            // Equipements hotel
            String sqlCreateEquipementsHotel = """
                    CREATE TABLE IF NOT EXISTS equipements_hotel (
                        id_hotel INTEGER NOT NULL,
                        id_equipement INTEGER NOT NULL,
                        PRIMARY KEY (id_hotel, id_equipement),
                        CONSTRAINT fk_equipements_hotel_equipement FOREIGN KEY(id_equipement) REFERENCES equipements(id_equipement),
                        CONSTRAINT fk_equipements_hotel_hotel FOREIGN KEY(id_hotel) REFERENCES hotels(id_hotel)
                    );""";

            // Option_hotel
            String sqlCreateOptionHotel = """
                    CREATE TABLE IF NOT EXISTS option_hotel (
                        id_option_hotel SERIAL PRIMARY KEY,
                        option_id INTEGER NOT NULL,
                        hotel_id INTEGER NOT NULL,
                        prix_option NUMERIC(10, 2),
                        CONSTRAINT fk_option_hotel_option FOREIGN KEY(option_id) REFERENCES options(id_option),
                        CONSTRAINT fk_option_hotel_hotel FOREIGN KEY(hotel_id) REFERENCES hotels(id_hotel)
                    );""";

            // Details_reservation
            String sqlCreateDetailsReservation = """
                    CREATE TABLE IF NOT EXISTS details_reservation (
                        id_details_reservation SERIAL PRIMARY KEY,
                        reservation_id INTEGER NOT NULL,
                        prix_total_details_reservation NUMERIC(10, 2),
                        id_chambre INTEGER,
                        CONSTRAINT fk_details_reservation_reservation FOREIGN KEY(reservation_id) REFERENCES reservations(id_reservation),
                        CONSTRAINT fk_details_reservation_chambre FOREIGN KEY(id_chambre) REFERENCES chambres(id_chambre)
                    );""";

            // Details_reservation_option_hotel
            String sqlCreateDetailsReservationOptionHotel = """
                    CREATE TABLE IF NOT EXISTS details_reservation_option_hotel (
                        id_option_hotel INTEGER NOT NULL,
                        details_reservation_id INTEGER NOT NULL,
                        PRIMARY KEY (id_option_hotel, details_reservation_id),
                        CONSTRAINT fk_details_reservation_option_hotel FOREIGN KEY(id_option_hotel) REFERENCES option_hotel(id_option_hotel),
                        CONSTRAINT fk_details_reservation_details FOREIGN KEY(details_reservation_id) REFERENCES details_reservation(id_details_reservation)
                    );""";




            // Exécution des requêtes de création de tables
            statement.execute(sqlCreateAdresses);
            statement.execute(sqlCreateOptions);
            statement.execute(sqlCreateEquipements);
            statement.execute(sqlCreateHotels);
            statement.execute(sqlCreateClients);
            statement.execute(sqlCreateChambres);
            statement.execute(sqlCreateReservations);
            statement.execute(sqlCreateEquipementsHotel);
            statement.execute(sqlCreateOptionHotel);
            statement.execute(sqlCreateDetailsReservation);
            statement.execute(sqlCreateDetailsReservationOptionHotel);

            System.out.println("Tables created successfully");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        } finally {
            DatabaseConnection.getInstance().close();
        }
    }

}
