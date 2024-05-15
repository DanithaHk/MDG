package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceTransport;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceTransportRepo {

    public static boolean placeTransport(PlaceTransport pt) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isTransportSaved = TransportRepo.save(pt.getTransport());
            System.out.println(isTransportSaved);
            if (isTransportSaved) {
                boolean isTransportDetailSaved = TransportDetailRepo.save(pt.getTdList());
                System.out.println("2"+isTransportDetailSaved);
                if (isTransportDetailSaved) {
                    connection.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}



