package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.ChambreList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ChambreDAO implements IChambreDAO {

    private Connection conn;
    private PreparedStatement getChambres;

    public ChambreDAO() throws SQLException {

        try {
            this.conn = DataSource.getInstance().getConnection();
            Statement statement = conn.createStatement();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS chambres (" +
                    "id_chambre integer NOT NULL DEFAULT nextval('chambre_id_chambre_seq'::regclass), " +
                    "numero_chambre integer NOT NULL, " +
                    "etage integer NOT NULL, " +
                    "numero_personnes integer NOT NULL, " +
                    "est_disponible boolean NOT NULL, " +
                    "photo_chambre text NOT NULL, " +
                    "type_champbre character varying(50) NOT NULL, " +
                    "lits integer NOT NULL, " +
                    "prix_chambre numeric NOT NULL, " +
                    "CONSTRAINT pk_chambre PRIMARY KEY (id_chambre)" +
                    ");";

            try {
                statement.executeUpdate(createTableSQL);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }catch (SQLException e ){
            throw new RuntimeException(e);
        }

        String requetSQL = "SELECT c.* FROM chambres c " +
                " INNER JOIN chambres ch ON c.id_chambre = ch.id_chambre " +
                " WHERE ch.id_hotel = ? AND c.est_disponible = true AND c.nombre_personnes >= ?;";
        try {
            this.getChambres = conn.prepareStatement(requetSQL);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChambreList getChambresDisponibles(int idHotel, LocalDate dateArrivee, LocalDate dateDepart, int nbPersonnes) {
        return null;
    }
}
