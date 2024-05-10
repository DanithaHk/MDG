package lk.ijse.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Client {

    private String id;
    private String name;
    private String address;
    private String cnumber;
    private String email;


    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", cnumber='" + cnumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
