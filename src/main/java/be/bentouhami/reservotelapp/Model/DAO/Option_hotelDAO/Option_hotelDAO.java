package be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Option;

import java.sql.*;
import java.util.ArrayList;

public class Option_hotelDAO implements IOption_hotelDAO {

    private final PreparedStatement getOptionPrixByHotelIdAndOptionId;
    private Connection connexion;
    private PreparedStatement getOptionsByHotelId;
    private PreparedStatement getOptionHotelByIdOptionAndIdHotel;

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
            this.getOptionHotelByIdOptionAndIdHotel = this.connexion.prepareStatement(
                    "SELECT id_option_hotel, option_id, hotel_id, prix_option" +
                            " FROM option_hotel " +
                            "WHERE hotel_id = ? AND option_id = ?");
            this.getOptionPrixByHotelIdAndOptionId = this.connexion.prepareStatement("SELECT prix_option " +
                    "FROM option_hotel" +
                    " WHERE hotel_id = ? AND option_id = ?");


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

    @Override
    public Option getOptionByIdOtpionAndHotelId(int idHotel, int idOption) {
        try {
            this.getOptionHotelByIdOptionAndIdHotel.setInt(1, idHotel);
            this.getOptionHotelByIdOptionAndIdHotel.setInt(2, idOption);
            ResultSet rs = this.getOptionHotelByIdOptionAndIdHotel.executeQuery();
            while (rs.next()) {
                //return new Option(rs.getInt())
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public double getOptionPrixByHotelIdAndOptionId(int hotelId, int idOption) {
        double prixOption = 0;
        try {
            this.getOptionPrixByHotelIdAndOptionId.setInt(1, hotelId);
            this.getOptionPrixByHotelIdAndOptionId.setInt(2, idOption);

            ResultSet rs = this.getOptionPrixByHotelIdAndOptionId.executeQuery();
            if (rs.next()) {
                prixOption = rs.getDouble("prix_option");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prixOption;
    }
}
