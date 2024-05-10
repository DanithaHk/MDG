package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;

import java.sql.*;

public class OrderRepo {

    public static String currentId() throws SQLException {
        String sql = "SELECT oid FROM orders ORDER BY oid desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, order.getOId());
        pstm.setString(2, order.getName());
        pstm.setDate(3, Date.valueOf(order.getDate()));
        pstm.setInt(4, order.getQty());
        pstm.setString(5, order.getCid());

        return pstm.executeUpdate() > 0;
    }
}
