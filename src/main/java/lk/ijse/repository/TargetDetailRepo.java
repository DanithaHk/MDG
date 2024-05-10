package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Client;
import lk.ijse.model.Employee;
import lk.ijse.model.Target;
import lk.ijse.model.TargetDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TargetDetailRepo {


    public static boolean save(TargetDetail targetDetail) throws SQLException {
        String sql = "INSERT INTO target_detail VALUES(?, ?, ?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, targetDetail.getId());
        pstm.setObject(2, targetDetail.getName());
        pstm.setObject(3, targetDetail.getDate());
        pstm.setObject(4, targetDetail.getTCoverOrNot());
        pstm.setObject(5, targetDetail.getEid());

        return pstm.executeUpdate() > 0;
    }


    public static List<TargetDetail> getAll() throws SQLException {
        String sql = "SELECT * FROM target_detail";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TargetDetail> targetDetailList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String tCoverOrNot = resultSet.getString(4);
            String eid = resultSet.getString(5);



            TargetDetail targetDetail = new TargetDetail(id, name, date, tCoverOrNot, eid);
            targetDetailList.add(targetDetail);
        }
        return targetDetailList;
    }

    public static Target searchById(String id) throws SQLException {
        String sql = "SELECT * FROM target WHERE tid = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Target(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }
}
