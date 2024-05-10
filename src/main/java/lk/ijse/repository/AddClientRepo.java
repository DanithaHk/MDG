package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddClientRepo {



    public static boolean save(Client client) throws SQLException {
        String sql = "INSERT INTO client VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

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


}

