package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    public static boolean save(Product product) throws SQLException {
        String sql = "INSERT INTO product VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, product.getId());
        pstm.setObject(2, product.getDesc());
        pstm.setObject(3, product.getCostPerUnit());

        return pstm.executeUpdate() > 0;
    }

    public static List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM product";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            double costPerOneShirt = Double.parseDouble(resultSet.getString(3));

            Product product = new Product(id, description, costPerOneShirt );
            productList.add(product);
        }
        return productList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM product WHERE pid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Product search(String id) throws SQLException {
        String sql = "SELECT * FROM product WHERE pid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        } else {
            return null;
        }
    }

    public static boolean update(Product product) throws SQLException {
        String sql = "UPDATE product SET description=?, costPerOneShirt=? WHERE pid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, product.getDesc());
        pstm.setObject(2, product.getCostPerUnit());
        pstm.setObject(3, product.getId());
        return pstm.executeUpdate() > 0;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT pid FROM product";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            idList.add(resultSet.getString(3));
        }
        return idList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT pid FROM product";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static Product searchById(String id) throws SQLException {
        String sql = "SELECT * FROM product WHERE pid = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;
    }
}

