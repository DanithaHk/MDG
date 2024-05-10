package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddProductRepo {
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

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String desc = resultSet.getString(2);
            Double costPerUnit = Double.valueOf(resultSet.getString(3));

            Product product = new Product(id, desc, costPerUnit);
            productList.add(product);
        }
        return productList;
    }
}
