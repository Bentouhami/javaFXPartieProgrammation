package be.bentouhami.reservotelapp.Model.DAO.Hotels;

import be.bentouhami.reservotelapp.Model.BL.HotelList;

public interface IHotelDAO {
    HotelList getHotels (String ville);

    boolean close();


}
