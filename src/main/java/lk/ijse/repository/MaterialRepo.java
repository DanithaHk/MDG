package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepo {

    public static boolean save(Material material) throws SQLException {
        String sql = "INSERT INTO material VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, material.getId());
        pstm.setObject(2, material.getDescription());
        pstm.setObject(3, material.getQty());
        pstm.setObject(4, material.getCostPerOne());

        return pstm.executeUpdate() > 0;
    }

    public static List<Material> getAll() throws SQLException {
        String sql = "SELECT * FROM material";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Material> materialList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            int qty = Integer.parseInt(resultSet.getString(3));
            double costPerUnit = Double.valueOf(resultSet.getString(4));

            Material material = new Material(id, description, qty, costPerUnit);
            materialList.add(material);
        }
        return materialList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM material WHERE mid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Material search(String id) throws SQLException {
        String sql = "SELECT * FROM material WHERE mid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Material(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)

                    );
        } else {
            return null;
        }
    }

    public static boolean update(Material material) throws SQLException {
        String sql = "UPDATE material SET description=?, qty=?, costPerOne=? WHERE mid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, material.getDescription());
        pstm.setObject(2, material.getQty());
        pstm.setObject(3, material.getCostPerOne());
        pstm.setObject(4, material.getId());

        return pstm.executeUpdate() > 0;
    }

}
