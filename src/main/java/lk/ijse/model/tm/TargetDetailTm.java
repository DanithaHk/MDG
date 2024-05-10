package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TargetDetailTm {
    private String id;
    private String name;
    private String date;
    private String tCoverOrNot;
    private String eid;
}
