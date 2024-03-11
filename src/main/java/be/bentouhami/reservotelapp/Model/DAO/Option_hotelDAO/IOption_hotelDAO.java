package be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IOption_hotelDAO {
    ArrayList<String[]> getOptionsByHotelId(String idHotel);

    double getOptionPrixByHotelIdAndOptionId(int hotelId, int idOption);

    int getOption_HotelID(int optionId, int idDetailsReservation);

    boolean close();

    void insertOptionHotel(int idOption, int idHotel, BigDecimal prixOption);
}
