package lk.ijse.model.tm;

import lk.ijse.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddClientTm  {
    private String id;
    private String name;
    private String address;
    private String cnumber;
    private String email;
}
