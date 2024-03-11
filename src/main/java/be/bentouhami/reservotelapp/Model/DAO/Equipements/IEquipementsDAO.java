package be.bentouhami.reservotelapp.Model.DAO.Equipements;

import be.bentouhami.reservotelapp.Model.BL.Equipement;
import be.bentouhami.reservotelapp.Model.BL.Hotel;

import java.util.ArrayList;
import java.util.List;

public interface IEquipementsDAO {
    void insertEquipementHotel(int idHotel, int idEquipement);

    ArrayList<String> getAllEquipements();

    ArrayList<Equipement> getHotelEquipementsByHotelId(Hotel hotelId);

    boolean close();

    void insert(Equipement equipement);

    List<Equipement> getEquipements();
}
