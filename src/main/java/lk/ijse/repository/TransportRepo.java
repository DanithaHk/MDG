package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Material;
import lk.ijse.model.Transport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportRepo {

    public static boolean save(Transport transport) throws SQLException {
        String sql = "INSERT INTO transport VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, transport.getTid());
        pstm.setObject(2, transport.getDate());
        pstm.setObject(3, transport.getArea());

        return pstm.executeUpdate() > 0;
    }

    public static List<Transport> getAll() throws SQLException {
        String sql = "SELECT * FROM transport";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Transport> transportList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String area = resultSet.getString(3);

            Transport transport = new Transport(id, date, area);
            transportList.add(transport);
        }
        return transportList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM transport WHERE tid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Transport search(String id) throws SQLException {
        String sql = "SELECT * FROM transport WHERE tid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Transport(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        } else {
            return null;
        }
    }

    public static boolean update(Transport transport) throws SQLException {
        String sql = "UPDATE transport SET date=?, area=? WHERE tid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, transport.getDate());
        pstm.setObject(2, transport.getArea());
        pstm.setObject(3, transport.getTid());

        return pstm.executeUpdate() > 0;
    }
}
