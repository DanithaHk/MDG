package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Employee {
    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private String jobRole;
    private String username;
}
