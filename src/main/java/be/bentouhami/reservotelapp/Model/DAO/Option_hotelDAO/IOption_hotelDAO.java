package be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO;

import be.bentouhami.reservotelapp.Model.BL.Option;

import java.util.ArrayList;

public interface IOption_hotelDAO {
    ArrayList<String[]> getOptionsByHotelId(String idHotel);

    Option getOptionByIdOtpionAndHotelId(int idHotel, int idOption);

    double getOptionPrixByHotelIdAndOptionId(int hotelId, int idOption);

    int getOption_HotelID(int optionId, int idDetailsReservation, double prixOption);
}
