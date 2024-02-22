package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import be.bentouhami.reservotelapp.Model.BL.ChambreList;

import java.util.ArrayList;

public interface IChambreDAO {

    ChambreList getChambresListByHotelId(int i);
    ArrayList<String> getChambreByIdAndHotelId(int idChambre, int idHotel);
}

