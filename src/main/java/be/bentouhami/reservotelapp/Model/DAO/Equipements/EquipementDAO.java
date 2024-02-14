package be.bentouhami.reservotelapp.Model.DAO.Equipements;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.sql.*;
import java.util.ArrayList;

public class EquipementDAO implements IEquipementDAO {

    private Connection connexion;
    private PreparedStatement getAllEquipements;

    public EquipementDAO() {
        try {
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Equipements" +
                        "(id_equipement SERIAL PRIMARY KEY," +
                        "equipement varchar(100) NOT NULL," +
                        " description_equipement TEXT NOT NULL)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            statement.close();
            this.getAllEquipements = this.connexion.prepareStatement
                    ("SELECT DISTINCT equipement FROM equipements" +
                            " ORDER BY equipement");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getAllEquipements() {
        ArrayList<String> eq = new ArrayList<>();
        try{
            ResultSet rs = this.getAllEquipements.executeQuery();
            while(rs.next()){
                eq.add(rs.getString("equipement"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return eq;
    }
}
