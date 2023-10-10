package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherRequest {
    private Long aisId;
    private String name;
    private String email;
    private String password;
    private String institute;
    private String department;
}
