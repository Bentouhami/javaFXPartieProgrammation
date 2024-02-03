package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import be.bentouhami.reservotelapp.Model.BL.ChambreList;

import java.time.LocalDate;

public interface IChambreDAO {
    ChambreList getChambresDisponibles(int idHotel,
                                       LocalDate dateArrivee,
                                       LocalDate dateDepart,
                                       int nbPersonnes);
}

