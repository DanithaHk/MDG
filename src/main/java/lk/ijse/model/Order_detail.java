package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order_detail {
    private String oid;
    private String cid;
    private String cNumber;
    private String pid;
    private String pName;
    private double unitPrice;
    private int qty;
    private String date;
    private String nededSwingCloth;
    private double total;
}
