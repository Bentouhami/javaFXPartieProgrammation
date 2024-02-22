package be.bentouhami.reservotelapp.Model.DAO.Option_hotelDAO;

import java.util.ArrayList;

public interface IOption_hotelDAO {
    ArrayList<String[]> getOptionsByHotelId(String idHotel);
}
