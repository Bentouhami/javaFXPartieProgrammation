package be.bentouhami.reservotelapp.Model.DAO.Options;

import be.bentouhami.reservotelapp.Model.BL.Option;

import java.util.List;

public interface IOptionsDAO {

    void insert(Option option);

    boolean close();

    List<Option> getOptions();
}
