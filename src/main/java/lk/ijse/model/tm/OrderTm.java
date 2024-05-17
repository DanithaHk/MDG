package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTm {
    private String oId;
    private String cId;
    private String pId;
    private String name;
    private double unitPrice;
    private int qty;
    private String date;


}
