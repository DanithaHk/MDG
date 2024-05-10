package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendence {
    private String id;
    private String name;
    private String date;
    private int presentOrNot;
    private String employeeId;

}
