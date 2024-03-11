package be.bentouhami.reservotelapp.Model.DAO.Options;

import be.bentouhami.reservotelapp.DataSource.DatabaseConnection;
import be.bentouhami.reservotelapp.Model.BL.Option;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionsDAO implements IOptionsDAO {

    private final PreparedStatement getAllOptionsByHotelId;
    private final PreparedStatement insert;
    private final PreparedStatement getOptions;
    private Connection connexion;

    public OptionsDAO() {
        try {
            this.connexion = DatabaseConnection.getInstance().getConnection();
            Statement statement = connexion.createStatement();

            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Hotels" +
                        "(id_hotel SERIAL " +
                        "        constraint pk_hotel" +
                        "            primary key," +
                        "    id_adresse integer NOT NULL," +
                        "    nom_hotel varchar(20)   not null," +
                        "    etoils integer not null" +
                        "        constraint ckc_etoils_hotel" +
                        "            check ((etoils >= 1) AND (etoils <= 5))," +
                        "    description_hotel    varchar(255)  not null," +
                        "    prix_chambre_minimum numeric(5, 2) not null," +
                        "    nombre_chambre       integer       not null," +
                        "    contact_telephone    varchar(20)   not null," +
                        "    photo_hotel          text          not null," +
                        "    contact_email        varchar(150)  not null" +
                        ");");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();
            this.insert = this.connexion.prepareStatement("""
                    INSERT INTO options (description_option, option) VALUES (?, ?)
                    """);

            this.getAllOptionsByHotelId = this.connexion.prepareStatement("SELECT op.* FROM options op " +
                    "JOIN option_hotel oph ON oph.option_id = op.id_option " +
                    " WHERE oph.hotel_id = ?;");
            this.getOptions = this.connexion.prepareStatement("""
                        SELECT id_option, description_option, option  FROM options;
                    """);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void insert(Option option) {
        try {
            this.insert.setString(1, option.getDescription_option());
            this.insert.setString(2, option.getOption());
            this.insert.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Option> getOptions() {
        List<Option> options = new ArrayList<>();
        try {
            ResultSet rs = this.getOptions.executeQuery();
            while (rs.next()) {
                options.add(new Option(rs.getInt("id_option"),
                        rs.getString("description_option"),
                        rs.getString("option")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return options;
    }

    @Override
    public boolean close() {
        boolean isSuccessful = true; // Pour suivre si toutes les fermetures ont réussi

        // Fermer getAllVillesByPays
        if (this.insert != null) {
            try {
                this.insert.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                isSuccessful = false;
            }
        }
        if (this.getOptions != null) {
            try {
                this.getOptions.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                isSuccessful = false;
            }
        }
        return isSuccessful;
    }

}
