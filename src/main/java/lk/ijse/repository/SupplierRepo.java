package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Material;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, supplier.getId());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getAddress());
        pstm.setObject(4, supplier.getContactNumber());
        pstm.setObject(5, supplier.getUsername());

        return pstm.executeUpdate() > 0;

    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supplierList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contactNumber = resultSet.getString(4);
            String username = resultSet.getString(5);

            Supplier supplier = new Supplier(id, name, address, contactNumber,username);
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public static Supplier search(String id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE spid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            );
        } else {
            return null;
        }

    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE spid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET name=?, address=?, contactNumber=?, username=? WHERE spid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getName());
        pstm.setObject(2, supplier.getAddress());
        pstm.setObject(3, supplier.getContactNumber());
        pstm.setObject(4, supplier.getUsername());
        pstm.setObject(5, supplier.getId());

        return pstm.executeUpdate() > 0;
    }
}
