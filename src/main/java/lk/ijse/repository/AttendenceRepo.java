package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Attendence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendenceRepo {


    public static boolean save(Attendence attendence) throws SQLException {
        String sql = "INSERT INTO attendance VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, attendence.getId());
        pstm.setObject(2, attendence.getName());
        pstm.setObject(3, attendence.getDate());
        pstm.setObject(4, attendence.getPresentOrNot());
        pstm.setObject(5, attendence.getEmployeeid());

        return pstm.executeUpdate() > 0;
    }


    public static List<Attendence> getAll() throws SQLException {
        String sql = "SELECT * FROM attendance  ";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Attendence> attendenceList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            int presentOrNot = Integer.parseInt(resultSet.getString(4));
            String employeeId = resultSet.getString(5);


            Attendence attendence = new Attendence(id, name, date, presentOrNot, employeeId);
            attendenceList.add(attendence);
        }
        return attendenceList;
    }

    public static Attendence search(String id) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE aid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Attendence(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)

            );
        } else {
            return null;
        }
    }

    public static boolean update(Attendence attendence) throws SQLException {
        String sql = "UPDATE attendance SET name=?, date=?, presentOrNot=?, eid=? WHERE aid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, attendence.getName());
        pstm.setObject(2, attendence.getDate());
        pstm.setObject(3, attendence.getPresentOrNot());
        pstm.setObject(4, attendence.getEmployeeid());
        pstm.setObject(5, attendence.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM attendance WHERE aid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Attendence searchByEId(String id) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE eid = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Attendence(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static int getdateCount(String id) throws SQLException {
        String sql = "SELECT Count(presentOrNot) FROM attendance WHERE eid = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, id);
        int i =0;
        ResultSet resultSet = pstm.executeQuery();
        while(resultSet.next()){
             return   i = resultSet.getInt(1);
        }
        return i;
    }

    public static List<Attendence> getTodayAttendence(String today) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE date = ? ";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, today);
        ResultSet resultSet = pstm.executeQuery();

        List<Attendence> attendenceList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            int presentOrNot = Integer.parseInt(resultSet.getString(4));
            String employeeId = resultSet.getString(5);


            Attendence attendence = new Attendence(id, name, date, presentOrNot, employeeId);
            attendenceList.add(attendence);
        }
        return attendenceList;

    }
}

