package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;
import lk.ijse.model.Order_detail;

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
        System.out.println(order);
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

    public static List<Order_detail> getAll() throws SQLException {
        String sql = "SELECT * FROM order_detail";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Order_detail> orderList = new ArrayList<>();
        while (resultSet.next()) {
            String oId = resultSet.getString(1);
            String cId = resultSet.getString(2);
            String contactNumber = resultSet.getString(3);
            String pId = resultSet.getString(4);
            String productName = resultSet.getString(5);
            double unitPrice = resultSet.getDouble(6);;
            int qty = resultSet.getInt(7);;
            String date = resultSet.getString(8);
            String nededSwingCloth = resultSet.getString(9);
            double total = resultSet.getDouble(10);

            Order_detail orderDetail = new Order_detail(oId,cId,contactNumber,pId,productName,unitPrice, qty,date,nededSwingCloth,total);
            orderList.add(orderDetail);
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
