package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order_detail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {

    public static boolean save(List<Order_detail> odList) throws SQLException {
        for (Order_detail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(Order_detail od) throws SQLException {
        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, od.getOid());
        pstm.setString(2, od.getCid());
        pstm.setString(3, od.getCNumber());
        pstm.setString(4, od.getPid());
        pstm.setString(5, od.getPName());
        pstm.setDouble(6, od.getUnitPrice());
        pstm.setInt(7, od.getQty());
        pstm.setDate(8, Date.valueOf(od.getDate()));
        pstm.setString(9, od.getNededSwingCloth());
        pstm.setDouble(10, od.getTotal());


        return pstm.executeUpdate() > 0;
    }
}
