package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Client;
import lk.ijse.model.MaterialDetail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDetailRepo {



    public static List<String> getMIds() throws SQLException {
        String sql = "SELECT description FROM material";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static boolean save(MaterialDetail materialDetail) throws SQLException {
        String sql = "INSERT INTO material_detail VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, materialDetail.getOId());
        pstm.setString(2, materialDetail.getDesc());
        pstm.setString(3, materialDetail.getMId());
        pstm.setInt(4, materialDetail.getMaterialQty());

        return pstm.executeUpdate() > 0;
    }
}
