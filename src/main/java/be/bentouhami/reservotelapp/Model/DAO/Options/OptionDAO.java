package be.bentouhami.reservotelapp.Model.DAO.Options;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Option;
import be.bentouhami.reservotelapp.Model.BL.OptionList;
import be.bentouhami.reservotelapp.Model.Services.Validator;

import java.sql.*;

public class OptionDAO implements IOptionDAO {

    private final PreparedStatement getAllOptionsByHotelId;
    private Connection conn;

    public OptionDAO() {
        try {
            this.conn = DataSource.getInstance().getConnection();
            Statement statement = conn.createStatement();

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
            this.getAllOptionsByHotelId = this.conn.prepareStatement("SELECT op.* FROM options op " +
                    "JOIN option_hotel oph ON oph.option_id = op.id_option " +
                    " WHERE oph.hotel_id = ?;");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
