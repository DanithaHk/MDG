package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Material;
import lk.ijse.model.Target;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TargetRepo {

    public static boolean save(Target target) throws SQLException {
        String sql = "INSERT INTO target VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, target.getId());
        pstm.setObject(2, target.getName());
        pstm.setObject(3, target.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static List<Target> getAll() throws SQLException {
        String sql = "SELECT * FROM target";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Target> targetList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = String.valueOf(resultSet.getDate(3));


            Target target = new Target(id, name, date);
            targetList.add(target);
        }
        return targetList;
    }
}
