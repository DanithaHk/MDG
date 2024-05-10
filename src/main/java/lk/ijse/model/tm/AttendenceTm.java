package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendenceTm {

        private String id;
        private String name;
        private String date;
        private int presentOrNot;
        private String employeeid;

}
