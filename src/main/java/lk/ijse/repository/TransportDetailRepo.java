package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.TransportDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransportDetailRepo {

    public static boolean save(List<TransportDetail> tdList) throws SQLException {
        for (TransportDetail td : tdList) {
            if(!save(td)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(TransportDetail td) throws SQLException {
        String sql = "INSERT INTO transport_detail VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, td.getTId());
        pstm.setString(2, td.getDate());
        pstm.setString(3, td.getEId());

        return pstm.executeUpdate() > 0;
    }

}
