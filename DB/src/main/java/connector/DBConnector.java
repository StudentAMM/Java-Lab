package connector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Утилитный класс подкючения к БД
 */
public class DBConnector {
    /**
     *
     */
    private static final String DB_PROPERTIES_FILE = "src/main/resources/db.properties";

    /**
     * Получение подключения к БД
     *
     * @return экземпляр {@link Connection}
     */
    public static Connection getConnection() throws SQLException,FileNotFoundException,IOException {
        Connection conn = null;
        Properties connectionProps = new Properties();

        FileInputStream fileInputStream = new FileInputStream(DB_PROPERTIES_FILE);
        connectionProps.load(fileInputStream);
        conn = DriverManager.getConnection(connectionProps.getProperty("url"), connectionProps);
        //System.out.println("Connected to database");

        /*catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE, "хм", ERROR_MESSAGE);
            //System.out.println("Exception has occured during creation of conenction");
            //e.printStackTrace();
        } catch (FileNotFoundException e) {
            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
            //e.printStackTrace();
        } catch (IOException e) {
            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
            //e.printStackTrace();
        }*/

        return conn;
    }
}