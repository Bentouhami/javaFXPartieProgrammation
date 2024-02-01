package be.bentouhami.reservotelapp.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataSource {
    private static DataSource instance;
    private Connection connection = null;

    private DataSource() throws SQLException, IOException {
        // Charger les propriétés à partir de config.properties
        Properties props = new Properties();
        try  {
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // Établir la connexion
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            throw ex; // re-throw the exception
        }
    }// end constructor
    public Connection getConnection() {
        return connection;
    }

    public static DataSource getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new DataSource();
        } else if (instance.getConnection().isClosed()) {
            instance = new DataSource();
        }

        return instance;
    }
}
