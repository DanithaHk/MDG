package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.AddOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class AddOrderRepo {

    public static boolean addOrder(AddOrder ad) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(ad.getOrder());
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailRepo.save(ad.getOdList());
                if (isOrderDetailSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
