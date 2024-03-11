package be.bentouhami.reservotelapp.DataSource;

import be.bentouhami.reservotelapp.Model.BL.*;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.AdressesDAO;
import be.bentouhami.reservotelapp.Model.DAO.Adresses.IAdressesDAO;
import be.bentouhami.reservotelapp.Model.DAO.Chambres.ChambresDAO;
import be.bentouhami.reservotelapp.Model.DAO.Chambres.IChambresDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.ClientsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Clients.IClientsDAO;
import be.bentouhami.reservotelapp.Model.DAO.DetailsReservations.DetailsReservationDAO;
import be.bentouhami.reservotelapp.Model.DAO.DetailsReservations.IDetails_reservationDAO;
import be.bentouhami.reservotelapp.Model.DAO.Details_reservation_option_hotelDAO.Details_reservation_option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Details_reservation_option_hotelDAO.IDetails_reservation_option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.EquipementsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Equipements.IEquipementsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.HotelsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Hotels.IHotelsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO.IOption_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO.Option_hotelDAO;
import be.bentouhami.reservotelapp.Model.DAO.Options.IOptionsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Options.OptionsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Reservations.IReservationsDAO;
import be.bentouhami.reservotelapp.Model.DAO.Reservations.ReservationsDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataInitializer {
    private static final Random random = new Random();
    private IHotelsDAO iHotelsDAO = new HotelsDAO();


    public DataInitializer() throws SQLException {

        IClientsDAO iClientsDAO = new ClientsDAO();
        IReservationsDAO iReservationsDAO = new ReservationsDAO();
        IOption_hotelDAO iOption_hotelDAO = new Option_hotelDAO();
        IDetails_reservationDAO iDetails_reservationDAO = new DetailsReservationDAO();
        IDetails_reservation_option_hotelDAO details_reservation_option_hotelDAO = new Details_reservation_option_hotelDAO();

        if (DatabaseUtils.isTableEmpty("adresses")) {
            IAdressesDAO iAdressesDAO = new AdressesDAO();

            // Insertion des données Adresses

            List<Adresse> adresses = Arrays.asList(
                    new Adresse("Rue de Bruxelles", "1", "0", "Bruxelles", "1000", "Belgique"),
                    new Adresse("Rue d’Anvers", "2", "0", "Anvers", "2000", "Belgique"),
                    new Adresse("Rue de Liège", "3", "0", "Liège", "4000", "Belgique"),
                    new Adresse("Rue de Gand", "4", "0", "Gand", "9000", "Belgique"),
                    new Adresse("Rue de Charleroi", "5", "0", "Charleroi", "6000", "Belgique"),
                    new Adresse("Rue de Casablanca", "1", "null", "Casablanca", "20000", "Maroc"),
                    new Adresse("Rue de Rabat", "2", "null", "Rabat", "10000", "Maroc"),
                    new Adresse("Rue de Fès", "3", "null", "Fès", "30000", "Maroc"),
                    new Adresse("Rue de Marrakech", "4", "null", "Marrakech", "40000", "Maroc"),
                    new Adresse("Rue de Tanger", "5", "null", "Tanger", "90000", "Maroc"),
                    new Adresse("Calle de Madrid", "1", "null", "Madrid", "28001", "Espagne"),
                    new Adresse("Calle de Barcelone", "2", "null", "Barcelone", "08001", "Espagne"),
                    new Adresse("Calle de Valence", "3", "null", "Valence", "46001", "Espagne"),
                    new Adresse("Calle de Séville", "4", "null", "Séville", "41001", "Espagne"),
                    new Adresse("Calle de Bilbao", "5", "null", "Bilbao", "48001", "Espagne"),
                    new Adresse("Rue de Tunis", "1", "null", "Tunis", "1000", "Tunisie"),
                    new Adresse("Rue de Sfax", "2", "null", "Sfax", "3000", "Tunisie"),
                    new Adresse("Rue de Sousse", "3", "null", "Sousse", "4000", "Tunisie"),
                    new Adresse("Rue de Djerba", "4", "null", "Djerba", "4180", "Tunisie"),
                    new Adresse("Rue de Bizerte", "5", "null", "Bizerte", "7000", "Tunisie"),
                    new Adresse("Rua de São Paulo", "1", "null", "São Paulo", "01000-000", "Brésil"),
                    new Adresse("Rua de Rio de Janeiro", "2", "null", "Rio de Janeiro", "20000-000", "Brésil"),
                    new Adresse("Rua de Salvador", "3", "null", "Salvador", "40000-000", "Brésil"),
                    new Adresse("Rua de Brasília", "4", "null", "Brasília", "70000-000", "Brésil"),
                    new Adresse("Rua de Fortaleza", "5", "null", "Fortaleza", "60000-000", "Brésil"),
                    new Adresse("Rue du Caire", "1", "null", "Le Caire", "11511", "Égypte"),
                    new Adresse("Rue d’Alexandrie", "2", "null", "Alexandrie", "21500", "Égypte"),
                    new Adresse("Rue de Gizeh", "3", "null", "Gizeh", "12556", "Égypte"),
                    new Adresse("Rue de Louxor", "4", "null", "Louxor", "85511", "Égypte"),
                    new Adresse("Rue d’Aswan", "5", "null", "Aswan", "81111", "Égypte")
            );

            for (Adresse adresse : adresses) {
                iAdressesDAO.insert(adresse);
            }
        }

        // insertion des options
        if (DatabaseUtils.isTableEmpty("options")) {
            IOptionsDAO iOptionsDAO = new OptionsDAO();
            List<Option> options = Arrays.asList(
                    new Option("Accès WiFi haut débit dans la chambre", "WiFi"),
                    new Option("Accès à la salle de sport 24h/24", "Salle de sport"),
                    new Option("'Accès au spa et à l' 'espace détente'", "Spa"),
                    new Option("Petit déjeuner servi en chambre chaque matin", "Petit déjeuné au lit"),
                    new Option("Service de navette aller-retour entre l’hôtel et l’aéroport", "Service de navette"),
                    new Option("Champagne, fleurs et chocolats dans la chambre à l’arrivée", "Pack romantique"),
                    new Option("Nettoyage et repassage des vêtements", "Service de blanchisserie")
            );

            for (Option option : options) {
                iOptionsDAO.insert(option);
            }
        }

        // insertion des equipements
        if (DatabaseUtils.isTableEmpty("equipements")) {
            IEquipementsDAO iEquipementsDAO = new EquipementsDAO();
            List<Equipement> equipements = Arrays.asList(
                    new Equipement("Piscine", "Piscine extérieure disponible toute l’année"),
                    new Equipement("Salle de jeux", "Espace de loisirs avec billard, jeux vidéo et plus"),
                    new Equipement("Sauna", "Sauna finlandais avec espace détente"),
                    new Equipement("Terrain de tennis", "Terrain de tennis extérieur disponible sur réservation"),
                    new Equipement("Espace enfants", "Zone de jeux et activités dédiée aux enfants de tous âges"),
                    new Equipement("Cours de fitness", "Séances de fitness et de yoga disponibles pour tous les clients")

            );

            for (Equipement equipement : equipements) {
                iEquipementsDAO.insert(equipement);
            }
        }

        if (DatabaseUtils.isTableEmpty("hotels")) {
            List<String> photoUrls = List.of(
                    "https://cdn.pixabay.com/photo/2018/08/06/19/49/design-3588214_1280.jpg",
                    "https://cdn.pixabay.com/photo/2017/08/06/14/56/people-2593251__340.jpg",
                    "https://cdn.pixabay.com/photo/2024/02/25/19/25/invalidendom-8596490__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/07/10/17/17/hotel-389256__340.jpg",
                    "https://cdn.pixabay.com/photo/2013/04/18/14/39/ship-105596__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/04/24/14/48/new-town-hall-4152279__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/10/18/09/02/hotel-1749602__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/03/09/06/30/pool-2128578__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/18/19/15/walkway-347319__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/21/14/56/bedroom-349698_1280.jpg",
                    "https://cdn.pixabay.com/photo/2020/10/18/09/16/bedroom-5664221_1280.jpg",
                    "https://cdn.pixabay.com/photo/2016/11/14/02/28/apartment-1822409__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/07/08/23/36/beach-house-1505461__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/09/18/03/28/travel-1677347__340.jpg",
                    "https://cdn.pixabay.com/photo/2015/11/06/11/45/interior-1026452__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/10/13/09/08/travel-1737171__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/06/10/01/05/hotel-room-1447201__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/01/14/12/48/hotel-1979406__340.jpg",
                    "https://cdn.pixabay.com/photo/2021/11/28/11/54/bed-6830011__340.jpg",
                    "https://cdn.pixabay.com/photo/2018/06/14/21/15/bedroom-3475656__340.jpg",
                    "https://cdn.pixabay.com/photo/2021/08/27/01/33/bedroom-6577523__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/08/19/13/58/bed-4416515__340.jpg",
                    "https://cdn.pixabay.com/photo/2020/10/18/09/16/bedroom-5664221__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/21/14/56/bedroom-349698__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/03/09/06/30/pool-2128578__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/18/19/15/walkway-347319__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/08/10/16/11/burj-al-arab-2624317__340.jpg",
                    "https://cdn.pixabay.com/photo/2018/08/06/19/49/design-3588214__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/10/13/09/08/travel-1737171__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/08/06/14/56/people-2593251__340.jpg ",
                    "https://cdn.pixabay.com/photo/2016/06/10/01/05/hotel-room-1447201__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/01/14/12/48/hotel-1979406__340.jpg",
                    "https://cdn.pixabay.com/photo/2024/02/25/19/25/invalidendom-8596490__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/07/10/17/17/hotel-389256__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/11/29/11/43/fountain-4661162__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/03/28/09/34/bedroom-1285156__340.jpg",
                    "https://cdn.pixabay.com/photo/2020/10/18/09/16/bedroom-5664221__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/04/24/14/48/new-town-hall-4152279__340.jpg",
                    "https://cdn.pixabay.com/photo/2013/04/18/14/39/ship-105596__340.jpg",
                    "https://cdn.pixabay.com/photo/2018/06/14/21/15/bedroom-3475656__340.jpg",
                    "https://cdn.pixabay.com/photo/2021/11/28/11/54/bed-6830011__340.jpg");
            IAdressesDAO iAdressesDAO = new AdressesDAO();
            List<Adresse> adresses = iAdressesDAO.getadresses();
            for (Adresse adresse : adresses) {
                int hotelCount = 2 + random.nextInt(15); // génère un nombre entre 2 et 10

                for (int i = 0; i < hotelCount; i++) {
                    int etoiles = 1 + random.nextInt(5); // Génère un nombre entre 1 et 5
                    String photoUrl = photoUrls.get(random.nextInt(photoUrls.size())); // Sélectionne une photo aléatoirement
                    String nomHotel = "Hôtel " + i + " - " + adresse.getVille() + " ";
                    String descriptionHotel = "Description pour " + nomHotel + ". Confort et qualité, étoiles : " + etoiles;
                    double prixChambreMin = 50.0 + (etoiles * 20.0); // Prix exemple basé sur le nombre d'étoiles
                    int nombreChambres = 10 + random.nextInt(50); // Entre 10 et 50 chambres
                    String contactTelephone = "012345678" + i;
                    String contactEmail = "contact@" + nomHotel.toLowerCase().replace(" ", "") + ".com";

                    // Crée un nouvel objet hôtel avec les informations générées
                    Hotel hotel = new Hotel(adresse.getIdAdresse(), nomHotel, etoiles, descriptionHotel, photoUrl, prixChambreMin, nombreChambres, contactTelephone, contactEmail);
                    // Insère l'hôtel dans la base de données
                    iHotelsDAO.insert(hotel);

                }
            }
        }

        if (DatabaseUtils.isTableEmpty("Chambres")) {
            IChambresDAO iChambresDAO = new ChambresDAO();
            List<String> photoUrls = List.of(
                    " https://cdn.pixabay.com/photo/2018/08/06/19/49/design-3588214_1280.jpg",
                    "https://cdn.pixabay.com/photo/2017/08/06/14/56/people-2593251__340.jpg",
                    "https://cdn.pixabay.com/photo/2024/02/25/19/25/invalidendom-8596490__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/07/10/17/17/hotel-389256__340.jpg",
                    "https://cdn.pixabay.com/photo/2013/04/18/14/39/ship-105596__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/04/24/14/48/new-town-hall-4152279__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/10/18/09/02/hotel-1749602__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/03/09/06/30/pool-2128578__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/18/19/15/walkway-347319__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/21/14/56/bedroom-349698_1280.jpg",
                    "https://cdn.pixabay.com/photo/2020/10/18/09/16/bedroom-5664221_1280.jpg",
                    "https://cdn.pixabay.com/photo/2016/11/14/02/28/apartment-1822409__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/07/08/23/36/beach-house-1505461__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/09/18/03/28/travel-1677347__340.jpg",
                    "https://cdn.pixabay.com/photo/2015/11/06/11/45/interior-1026452__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/10/13/09/08/travel-1737171__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/06/10/01/05/hotel-room-1447201__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/01/14/12/48/hotel-1979406__340.jpg",
                    "https://cdn.pixabay.com/photo/2021/11/28/11/54/bed-6830011__340.jpg",
                    "https://cdn.pixabay.com/photo/2018/06/14/21/15/bedroom-3475656__340.jpg",
                    "https://cdn.pixabay.com/photo/2021/08/27/01/33/bedroom-6577523__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/08/19/13/58/bed-4416515__340.jpg",
                    "https://cdn.pixabay.com/photo/2020/10/18/09/16/bedroom-5664221__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/21/14/56/bedroom-349698__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/03/09/06/30/pool-2128578__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/05/18/19/15/walkway-347319__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/08/10/16/11/burj-al-arab-2624317__340.jpg",
                    "https://cdn.pixabay.com/photo/2018/08/06/19/49/design-3588214__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/10/13/09/08/travel-1737171__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/08/06/14/56/people-2593251__340.jpg ",
                    "https://cdn.pixabay.com/photo/2016/06/10/01/05/hotel-room-1447201__340.jpg",
                    "https://cdn.pixabay.com/photo/2017/01/14/12/48/hotel-1979406__340.jpg",
                    "https://cdn.pixabay.com/photo/2024/02/25/19/25/invalidendom-8596490__340.jpg",
                    "https://cdn.pixabay.com/photo/2014/07/10/17/17/hotel-389256__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/11/29/11/43/fountain-4661162__340.jpg",
                    "https://cdn.pixabay.com/photo/2016/03/28/09/34/bedroom-1285156__340.jpg",
                    "https://cdn.pixabay.com/photo/2020/10/18/09/16/bedroom-5664221__340.jpg",
                    "https://cdn.pixabay.com/photo/2019/04/24/14/48/new-town-hall-4152279__340.jpg",
                    "https://cdn.pixabay.com/photo/2013/04/18/14/39/ship-105596__340.jpg",
                    "https://cdn.pixabay.com/photo/2018/06/14/21/15/bedroom-3475656__340.jpg",
                    "https://cdn.pixabay.com/photo/2021/11/28/11/54/bed-6830011__340.jpg");
            List<Hotel> hotels = iHotelsDAO.getAllHotels();
            for (Hotel hotel : hotels) {
                for (int i = 1; i <= 30; i++) { // Générer 100 chambres par hôtel
                    String numeroChambre = String.valueOf(i);
                    int etage = (i - 1) / 20 + 1; // Exemple d'assignation d'étage
                    int nombrePersonnes = 1 + random.nextInt(2); // 1 ou 2 personnes
                    boolean estDisponible = true;
                    String photoChambre = photoUrls.get(random.nextInt(photoUrls.size()));
                    String typeChambre = "duplexe";
                    String lits = nombrePersonnes == 1 ? "un lit" : "deux lits";
                    double prixChambre = genererPrixChambre(hotel.getEtoils()); // À implémenter

                    Chambre chambre = new Chambre(hotel.getIdHotel(),
                            numeroChambre,
                            etage,
                            nombrePersonnes,
                            estDisponible,
                            photoChambre,
                            typeChambre,
                            lits,
                            prixChambre);

                    iChambresDAO.insert(chambre);
                }
            }
        }

        if (DatabaseUtils.isTableEmpty("equipements_hotel")) {
            IEquipementsDAO iEquipementsDAO = new EquipementsDAO();
            List<Equipement> tousLesEquipements = iEquipementsDAO.getEquipements();

            // Convertissons la liste d'objets Equipement en une liste d'Integer pour les IDs d'équipements
            List<Integer> tousLesIdsEquipements = new ArrayList<>();
            for (Equipement equipement : tousLesEquipements) {
                tousLesIdsEquipements.add(equipement.getId_equipement());
            }

            List<Hotel> tousLesHotels = iHotelsDAO.getAllHotels();
            for (Hotel hotel : tousLesHotels) {
                List<Integer> idsEquipementsAAttribuer = new ArrayList<>();
                if (hotel.getEtoils() >= 3) {
                    // Pour les hôtels de 3, 4, et 5 étoiles, attribuer tous les équipements
                    idsEquipementsAAttribuer.addAll(tousLesIdsEquipements);
                } else {
                    // Pour les hôtels de 1 et 2 étoiles, choisir arbitrairement deux équipements
                    idsEquipementsAAttribuer.add(tousLesIdsEquipements.get(0));
                    idsEquipementsAAttribuer.add(tousLesIdsEquipements.get(1));
                }

                // Insertion dans la base de données pour chaque équipement sélectionné pour cet hôtel
                for (Integer idEquipement : idsEquipementsAAttribuer) {
                    iEquipementsDAO.insertEquipementHotel(hotel.getIdHotel(), idEquipement);
                }
            }
        }

        if (DatabaseUtils.isTableEmpty("option_hotel")) {
            IOption_hotelDAO iOptionHotelDAO = new Option_hotelDAO();
            IOptionsDAO iOptionsDAO = new OptionsDAO();
            List<Option> toutesLesOptions = iOptionsDAO.getOptions();

            List<Hotel> tousLesHotels = iHotelsDAO.getAllHotels();

            for (Hotel hotel : tousLesHotels) {
                // Attribuer des options gratuites (WiFi, Salle de sport, Spa) aux hôtels 4 et 5 étoiles
                if (hotel.getEtoils() >= 4) {
                    toutesLesOptions.stream()
                            .filter(option -> option.getId_option() == 1 || option.getId_option() == 2 || option.getId_option() == 3)
                            .forEach(option -> iOptionHotelDAO.insertOptionHotel(option.getId_option(), hotel.getIdHotel(), BigDecimal.ZERO));
                }

                // Attribuer Petit déjeuner au lit et Service de navette à un prix réduit pour les hôtels 5 étoiles
                if (hotel.getEtoils() == 5) {
                    toutesLesOptions.stream()
                            .filter(option -> option.getId_option() == 4 || option.getId_option() == 5)
                            .forEach(option -> {
                                BigDecimal prixOption = option.getId_option() == 4 ? BigDecimal.valueOf(20.00) : BigDecimal.valueOf(50.00);
                                iOptionHotelDAO.insertOptionHotel(option.getId_option(), hotel.getIdHotel(), prixOption);
                            });

                    // Attribuer Pack romantique et Service de blanchisserie à des prix premium pour les hôtels 5 étoiles
                    toutesLesOptions.stream()
                            .filter(option -> option.getId_option() == 6 || option.getId_option() == 7)
                            .forEach(option -> {
                                BigDecimal prixOption = option.getId_option() == 6 ? BigDecimal.valueOf(100.00) : BigDecimal.valueOf(30.00);
                                iOptionHotelDAO.insertOptionHotel(option.getId_option(), hotel.getIdHotel(), prixOption);
                            });
                }

                // Tu peux étendre ici pour attribuer des options spécifiques aux hôtels de 1 à 3 étoiles, si nécessaire
            }
        }


    }

    private double genererPrixChambre(int etoiles) {
        Random random = new Random();
        double prixChambre = 0.0;

        switch (etoiles) {
            case 1:
                // Pour les hôtels 1 étoile, les prix sont 60, 70, ou 80
                double[] prix1Etoile = {60, 70, 80};
                prixChambre = prix1Etoile[random.nextInt(prix1Etoile.length)];
                break;
            case 2:
            case 3:
                // Pour les hôtels de 2 et 3 étoiles, les prix vont de 80 à 120 par incréments de 10
                double[] prix2Et3Etoiles = {80, 90, 100, 110, 120};
                prixChambre = prix2Et3Etoiles[random.nextInt(prix2Et3Etoiles.length)];
                break;
            case 4:
            case 5:
                // Pour les hôtels de 4 et 5 étoiles, les prix vont de 120 à 170 par incréments de 10
                double[] prix4Et5Etoiles = {120, 130, 140, 150, 160, 170};
                prixChambre = prix4Et5Etoiles[random.nextInt(prix4Et5Etoiles.length)];
                break;
            default:
                System.out.println("Nombre d'étoiles non valide.");
                break;
        }

        return prixChambre;
    }

}
