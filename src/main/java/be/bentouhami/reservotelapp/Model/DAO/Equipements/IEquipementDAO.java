package be.bentouhami.reservotelapp.Model.DAO.Equipements;

import be.bentouhami.reservotelapp.Model.BL.Equipement;
import be.bentouhami.reservotelapp.Model.BL.Hotel;

import java.util.ArrayList;

public interface IEquipementDAO {
    ArrayList<String> getAllEquipements();

    ArrayList<Equipement> getHotelEquipementsByHotelId(Hotel hotelId);
}
