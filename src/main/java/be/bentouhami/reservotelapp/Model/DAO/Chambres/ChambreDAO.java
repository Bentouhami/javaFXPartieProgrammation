package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Chambre;
import be.bentouhami.reservotelapp.Model.BL.ChambreList;

import java.sql.*;

public class ChambreDAO implements IChambreDAO {

    private Connection conn;
    private PreparedStatement getChambres;
    private PreparedStatement getChambresByHotelId;

    public ChambreDAO() throws SQLException {

        try {
                this.conn = DataSource.getInstance().getConnection();
                Statement statement = conn.createStatement();
            try{

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS chambres (" +
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
                        ");");

            } catch (SQLException e) {
                throw new SQLException(e);
            }
                statement.close();
            String requetSQL = "SELECT c.* FROM chambres c " +
                        " INNER JOIN hotels h ON c.hotel_id = h.id_hotel " +
                        " WHERE h.id_hotel = ? AND c.est_disponible = true;";
            this.getChambresByHotelId = this.conn.prepareStatement(requetSQL);


            } catch (SQLException e) {
            throw new SQLException(e);
        }



        //this.getChambresByHotelId =
    }

    @Override
    public ChambreList getChambre(int id_hotel) {
        ChambreList ch = new ChambreList();
        try {
            this.getChambresByHotelId.setInt(1, id_hotel);
            ResultSet rs = this.getChambresByHotelId.executeQuery();

            while(rs.next()){
                ch.add(new Chambre(rs.getInt("id_chambre"),
                        rs.getInt("hotel_id"),
                        rs.getString("numero_chambre"),
                        rs.getInt("etage"),
                        rs.getInt("nombre_personnes"),
                        rs.getBoolean("est_disponible"),
                        rs.getString("photo_chambre"),
                        rs.getString("type_chambre"),
                        rs.getString("lits"),
                        rs.getDouble("prix_chambre")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ch;
    }
}
