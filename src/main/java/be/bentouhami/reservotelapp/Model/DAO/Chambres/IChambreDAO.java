package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import java.util.ArrayList;

public interface IChambreDAO {

    ArrayList<String[]> getChambresListByHotelId(int i);
    ArrayList<String> getChambreByIdAndHotelId(int idChambre, int idHotel);
}

