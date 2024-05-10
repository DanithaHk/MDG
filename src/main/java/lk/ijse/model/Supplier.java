package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {

    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private String username;
}
