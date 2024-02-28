package be.bentouhami.reservotelapp.Model.DAO.Reservations;

import be.bentouhami.reservotelapp.DataSource.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationDAO implements IReservationDAO {
    private Connection connexion;
    private PreparedStatement getReservations;
    private PreparedStatement writeReservations;


    public ReservationDAO() {
        try{
            this.connexion = DataSource.getInstance().getConnection();
            Statement statement = this.connexion.createStatement();
            statement.executeUpdate("create table if not exists reservations " +
                    "( id_reservation serial primary key, " +
                    "    client_id              integer        not null, " +
                    "    statut_reservation     varchar(50)    not null, " +
                    "    date_arrive            date           not null, " +
                    "    date_depart            date           not null, " +
                    "    prix_total_reservation numeric(10, 2) not null " +
                    ");");

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean getReservations() {
        return false;
    }

    @Override
    public boolean writeReservations() {
        return false;
    }
}
