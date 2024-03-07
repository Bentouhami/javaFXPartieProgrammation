package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import be.bentouhami.reservotelapp.Model.BL.Chambre;

import java.util.ArrayList;

public interface IChambreDAO {

    ArrayList<String[]> getChambresListByHotelId(int i);

    ArrayList<String> getChambreDatadByIdAndHotelId(int idChambre, int idHotel);

    Chambre getChambreByIdAndHotelId(int idChambre, int idHotel);

}

