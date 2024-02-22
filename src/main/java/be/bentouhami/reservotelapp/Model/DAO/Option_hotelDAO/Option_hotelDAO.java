package be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.sql.*;
import java.util.ArrayList;

public class Option_hotelDAO implements IOption_hotelDAO {

    private Connection connexion;
    private PreparedStatement getOptionsByHotelId;

    public Option_hotelDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate("create table if not exists option_hotel (" +
                        " id_option_hotel serial primary key," +
                        " option_id integer not null constraint fk_option_hotel_option references options," +
                        " hotel_id integer not null constraint fk_option_hotel_hotel references hotels," +
                        " prix_option numeric(10, 2))");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();
            this.getOptionsByHotelId = this.connexion.prepareStatement(
                    "SELECT option_id, option, description_option, oh.prix_option" +
                            " FROM options " +
                            "INNER JOIN option_hotel oh on options.id_option = oh.option_id " +
                            "WHERE hotel_id = ?; ");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String[]> getOptionsByHotelId(String idHotel) {
        ArrayList<String[]> optionsList = new ArrayList<>();
        try {
            this.getOptionsByHotelId.setInt(1, Integer.parseInt(idHotel));
            ResultSet rs = this.getOptionsByHotelId.executeQuery();
            while (rs.next()) {
                String[] optionDetails = new String[5]; // Tableau pour stocker les détails de l'option
                optionDetails[0] = idHotel;
                optionDetails[1] = String.valueOf(rs.getInt("option_id"));
                optionDetails[2] = rs.getString("option");
                optionDetails[3] = rs.getString("description_option");
                optionDetails[4] = String.format("%.2f", rs.getDouble("prix_option")); // Formatte le prix avec deux décimales

                optionsList.add(optionDetails);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionsList;
    }
}
