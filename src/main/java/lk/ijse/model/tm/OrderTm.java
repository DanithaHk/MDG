package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTm {
    private String oId;
    private String name;
    private String date;
    private int qty;
    private int cid;
    private double total;
}
