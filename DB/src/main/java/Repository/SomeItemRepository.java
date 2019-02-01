package Repository;

import connector.DBConnector;
import models.Item;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SomeItemRepository {

    private static final String DB_PROPERTIES_FILE = "src/main/resources/db.properties";

    public List<Item> getAll(String tableName) throws SQLException, FileNotFoundException, IOException {
        List<Item> items = new ArrayList<Item>();
        String query = "select t.* from public.\"" + tableName + "\" as t";
        selectFromtable(items, query);
        return items;
    }

    /**
     * @param tableName
     * @param id
     * @param flag      true - Descendants; false - Ancestry
     * @return
     */
    public List<Item> getAncestryOrDescendants(String tableName, int id, boolean flag) throws SQLException, FileNotFoundException, IOException {
        //getAncestryOrdescendants(null, true);
        List<Item> items = new ArrayList<Item>();
        String fromTable;
        if (flag) {
            fromTable = "FROM public.\"" + tableName + "\" AS t JOIN r ON t.\"parentId\" = r.\"idPerson\" "; //потомки
        } else
            fromTable = "FROM public.\"" + tableName + "\" AS t JOIN r ON t.\"idPerson\" = r.\"parentId\" "; // предки
        String query = "WITH RECURSIVE r AS (" +
                "    SELECT t.\"idPerson\", t.\"someInfo\", t.\"dateBirthday\", t.\"parentId\", 1 as i " +
                "    FROM public.\"" + tableName + "\" as t " +
                "WHERE t.\"idPerson\" = " + id + " " +
                "    UNION " +
                "    SELECT t.\"idPerson\", t.\"someInfo\", t.\"dateBirthday\", t.\"parentId\", i+1 AS i " +
                fromTable +
                "    WHERE i < 10" +
                " ) " +
                "SELECT r.\"idPerson\", r.\"someInfo\", r.\"dateBirthday\", r.\"parentId\" FROM r;";
        selectFromtable(items, query);
        return items;
    }


    public List<Item> getNeedLike(String tableName, String colomn, String reg) throws SQLException, FileNotFoundException, IOException {
        List<Item> items = new ArrayList<Item>();
        String query = "select t.* from public.\"" + tableName + "\" as t where  CAST(t.\"" + colomn + "\"AS TEXT) like '%" + reg + "%'";
        selectFromtable(items, query);
        return items;
    }


    private void selectFromtable(List<Item> items, String query) throws SQLException, FileNotFoundException, IOException {
        Connection connection = DBConnector.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            items.add(new Item(
                    resultSet.getInt("idPerson"),
                    resultSet.getString("someInfo"),
                    resultSet.getDate("dateBirthday"),
                    resultSet.getInt("parentId")
            ));
        }
    }

    /**
     * @param tableName
     * @return
     */
    public void insert(String tableName, Item item) throws SQLException, FileNotFoundException, IOException {
        //String query = "insert into " + tableName + " (idPerson, info) values(?,?)";
        String query = "insert into public.\"" + tableName + "\" (\"someInfo\", \"dateBirthday\", \"parentId\") values(?,?,?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = DBConnector.getConnection();
        statement = connection.prepareStatement(query);
        statement.setString(1, item.getSomeInfo());
        statement.setDate(2, item.getDate());
        if (item.getParentId() == 0)
            statement.setNull(3, item.getParentId());
        else
            statement.setInt(3, item.getParentId());
        statement.executeUpdate();


    }

    /**
     * @param tableName
     * @return
     */
    public void update(String tableName, Item item) throws SQLException, FileNotFoundException, IOException {
        String query = "update public.\"" + tableName + "\" set \"someInfo\"=?, \"dateBirthday\"=?,\"parentId\"=? where \"idPerson\"=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = DBConnector.getConnection();
        statement = connection.prepareStatement(query);
        statement.setString(1, item.getSomeInfo());
        statement.setDate(2, item.getDate());
        //statement.setInt(3, item.getParentidPerson());
        if (item.getParentId() == 0)
            statement.setNull(3, item.getParentId());
        else
            statement.setInt(3, item.getParentId());
        statement.setInt(4, item.getId());
        statement.executeUpdate();
    }


    /**
     * @param tableName
     * @return
     */
    public void delete(String tableName, int idPerson) throws SQLException, FileNotFoundException, IOException {
        //String query = "delete from " + tableName + " where idPerson=" + idPerson;
        String query = "delete from public.\"" + tableName + "\" where \"idPerson\"=" + idPerson;
        sorryForMyBadCode(query);
    }

    public void sinchro(String pTable, String cTable) throws SQLException, FileNotFoundException, IOException {
        clear(cTable);
        String query = "INSERT INTO \"" + cTable + "\" (\"idPerson\", \"someInfo\", \"dateBirthday\", \"parentId\")" +
                "SELECT \"idPerson\", \"someInfo\", \"dateBirthday\", \"parentId\" FROM \"" + pTable + "\"";
        sorryForMyBadCode(query);
    }

    public void clear(String tableName) throws SQLException, FileNotFoundException, IOException {
        String query = "DELETE FROM \"" + tableName + "\"";
        sorryForMyBadCode(query);
    }

    private void sorryForMyBadCode(String query) throws SQLException, FileNotFoundException, IOException {
        Connection connection = DBConnector.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

    }

}
