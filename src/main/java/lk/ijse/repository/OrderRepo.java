package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;
import lk.ijse.model.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static Order search(String id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE oid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        } else {
            return null;
        }
    }

    public static boolean update(Order order) throws SQLException {
        String sql = "UPDATE orders SET  name=?, date=?, qty=?, cid=? WHERE oid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, order.getName());
        pstm.setObject(2, order.getDate());
        pstm.setObject(3, order.getQty());
        pstm.setObject(4, order.getCid());
        pstm.setObject(5, order.getOId());

        return pstm.executeUpdate() > 0;

    }

    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()) {
            String oId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            int qty = Integer.parseInt(resultSet.getString(4));
            String cid = resultSet.getString(5);

            Order order = new Order(oId,name, date, qty, cid);
            orderList.add(order);
        }
        return orderList;

    }

    public static boolean delete(String oId) throws SQLException {
        String sql = "DELETE FROM orders WHERE oid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, oId);

        return pstm.executeUpdate() > 0;
    }
}
