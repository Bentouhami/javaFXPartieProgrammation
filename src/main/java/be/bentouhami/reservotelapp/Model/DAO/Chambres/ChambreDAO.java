package be.bentouhami.reservotelapp.Model.DAO.Chambres;

import be.bentouhami.reservotelapp.DataSource.DataSource;
import be.bentouhami.reservotelapp.Model.BL.Chambre;

import java.sql.*;
import java.util.ArrayList;

public class ChambreDAO implements IChambreDAO {

    private Connection conn;
    private PreparedStatement getChambreByIdAndHotelId;
    private PreparedStatement getChambresListByHotelId;

    public ChambreDAO() throws SQLException {

        try {
            this.conn = DataSource.getInstance().getConnection();
            Statement statement = conn.createStatement();
            try {

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
            this.getChambresListByHotelId = this.conn.prepareStatement(requetSQL);

            this.getChambreByIdAndHotelId = this.conn.prepareStatement(
                    "SELECT c.* FROM chambres c" +
                            " WHERE c.id_chambre = ? AND c.hotel_id = ?");


        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }

    @Override
    public ArrayList<String[]> getChambresListByHotelId(int id_hotel) {
        ArrayList<String[]> ch = new ArrayList<>();
        try {
            this.getChambresListByHotelId.setInt(1, id_hotel);
            ResultSet rs = this.getChambresListByHotelId.executeQuery();

            while (rs.next()) {
                String[] chambresArrStr = new String[10];
                chambresArrStr[0] = String.valueOf(rs.getInt("id_chambre")); // 0
                chambresArrStr[1] = String.valueOf(rs.getInt("hotel_id")); // 1
                chambresArrStr[2] = rs.getString("numero_chambre"); // 2
                chambresArrStr[3] = String.valueOf(rs.getInt("etage")); // 3
                chambresArrStr[4] = String.valueOf(rs.getInt("nombre_personnes")); // 4
                chambresArrStr[5] = String.valueOf(rs.getBoolean("est_disponible")); // 5
                chambresArrStr[6] = rs.getString("photo_chambre"); // 6
                chambresArrStr[7] = rs.getString("type_chambre"); // 7
                chambresArrStr[8] = rs.getString("lits"); // 8
                chambresArrStr[9] = String.valueOf(rs.getDouble("prix_chambre")); // 9
                ch.add(chambresArrStr);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ch;
    }

    @Override
    public ArrayList<String> getChambreDatadByIdAndHotelId(int idChambre, int idHotel) {
        ArrayList<String> chambreDatas = new ArrayList<>();
        try {
            this.getChambreByIdAndHotelId.setInt(1, idChambre);
            this.getChambreByIdAndHotelId.setInt(2, idHotel);
            ResultSet rs = this.getChambreByIdAndHotelId.executeQuery();
            while (rs.next()) {
                chambreDatas.add(String.valueOf(rs.getInt("id_chambre"))); // 0
                chambreDatas.add(String.valueOf(rs.getInt("hotel_id"))); // 1
                chambreDatas.add(rs.getString("numero_chambre")); // 2
                chambreDatas.add(rs.getString("etage")); // 3
                chambreDatas.add(String.valueOf(rs.getInt("nombre_personnes"))); // 4
                chambreDatas.add(String.valueOf(rs.getBoolean("est_disponible"))); // 5
                chambreDatas.add(rs.getString("photo_chambre")); // 6
                chambreDatas.add(rs.getString("type_chambre")); // 7
                chambreDatas.add(rs.getString("lits")); // 8
                chambreDatas.add(String.valueOf(rs.getDouble("prix_chambre"))); // 9
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chambreDatas;
    }

    @Override
    public Chambre getChambreByIdAndHotelId(int idChambre, int idHotel) {
        try {
            this.getChambreByIdAndHotelId.setInt(1, idChambre);
            this.getChambreByIdAndHotelId.setInt(2, idHotel);
            ResultSet rs = this.getChambreByIdAndHotelId.executeQuery();
            while (rs.next()) {
                int id_Chambre = rs.getInt("id_chambre");
                int hotel_id = rs.getInt("hotel_id");
                String numero_chambre = rs.getString("numero_chambre");
                int etage = rs.getInt("etage");
                int nombre_personnes = rs.getInt("nombre_personnes");
                boolean est_diponible = rs.getBoolean("est_disponible");
                String photo_chambre = rs.getString("photo_chambre");
                String type_chambre = rs.getString("type_chambre");
                String lits = rs.getString("lits");
                double prix_chambre = rs.getDouble("prix_chambre");

                return new Chambre(id_Chambre,
                        hotel_id,
                        numero_chambre,
                        etage,
                        nombre_personnes,
                        est_diponible,
                        photo_chambre,
                        type_chambre,
                        lits,
                        prix_chambre);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
