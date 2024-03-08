package be.bentouhami.reservotelapp.Model.DAO.Hotels;

import be.bentouhami.reservotelapp.Model.BL.Hotel;
import be.bentouhami.reservotelapp.Model.BL.HotelList;

import java.util.ArrayList;

public interface IHotelDAO {
    HotelList getHotels (String ville);

    boolean close();
    ArrayList<String> getAllPrix();

    Hotel getHotelById(String hotelId);
}
