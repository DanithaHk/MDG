package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {

    public static String currentId() throws SQLException {
        String sql = "SELECT sid FROM salary ORDER BY sid desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;

    }

    public static List<Salary> getAll() throws SQLException {
        String sql = "SELECT * FROM salary";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Salary> salaryList = new ArrayList<>();
        while (resultSet.next()) {
            String sid = resultSet.getString(1);
            String eid = resultSet.getString(2);
            String name = resultSet.getString(3);
            String date = resultSet.getString(4);
            double basicSalary = Double.parseDouble(resultSet.getString(5));
            double bouns = Double.parseDouble(resultSet.getString(6));
            double total = Double.parseDouble(resultSet.getString(7));

            Salary salary = new Salary(sid,eid, name, date, basicSalary,bouns,total);
            salaryList.add(salary);
        }
        return salaryList;

    }

    public static boolean save(Salary salary) throws SQLException {
        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        System.out.println(salary.getEId());
        pstm.setObject(1, salary.getSId());
        pstm.setObject(2, salary.getEId());
        pstm.setObject(3, salary.getName());
        pstm.setObject(4, salary.getDate());
        pstm.setObject(5, salary.getBasicSalary());
        pstm.setObject(6, salary.getBouns());
        pstm.setObject(7, salary.getTotal());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM salary WHERE sid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Salary search(String id) throws SQLException {

        String sql = "SELECT * FROM salary WHERE sid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7)
            );
        } else {
            return null;
        }
    }

    public static boolean update(Salary salary) throws SQLException {
        String sql = "UPDATE salary SET eid=?, name=?, date=?, basicSalary=?, bouns=?, total=? WHERE sid=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, salary.getEId());
        pstm.setObject(2, salary.getName());
        pstm.setObject(3, salary.getDate());
        pstm.setObject(4, salary.getBasicSalary());
        pstm.setObject(5, salary.getBouns());
        pstm.setObject(6, salary.getTotal());
        pstm.setObject(7, salary.getSId());


        return pstm.executeUpdate() > 0;

    }
}
