package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Attendence;
import lk.ijse.model.Client;
import lk.ijse.model.Employee;
import lk.ijse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo {
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM client WHERE cid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Client client) throws SQLException {
        String sql = "INSERT INTO client VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, client.getId());
        pstm.setObject(2, client.getName());
        pstm.setObject(3, client.getAddress());
        pstm.setObject(4, client.getCnumber());
        pstm.setObject(5, client.getEmail());

        return pstm.executeUpdate() > 0;
    }

    public static List<Client> getAll() throws SQLException {
        String sql = "SELECT * FROM client";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Client> clientList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String email = resultSet.getString(5);

            Client client = new Client(id, name, address, tel,email);
            clientList.add(client);
        }
        return clientList;
    }

    public static Client search(String id) throws SQLException {
        String sql = "SELECT * FROM client WHERE cid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Client(
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

    public static boolean update(Client client) throws SQLException {
        String sql = "UPDATE client SET name=?, address=?, contactNumber=?, email=? WHERE cid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, client.getName());
        pstm.setObject(2, client.getAddress());
        pstm.setObject(3, client.getCnumber());
        pstm.setObject(4, client.getEmail());
        pstm.setObject(5, client.getId());



        return pstm.executeUpdate() > 0;
    }

    public static Client searchBycNumber(String cNumber) throws SQLException {
        String sql = "SELECT * FROM client WHERE contactNumber = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, cNumber);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Client(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }
}
