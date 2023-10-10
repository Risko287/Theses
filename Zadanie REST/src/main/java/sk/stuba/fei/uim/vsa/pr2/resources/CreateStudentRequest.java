package sk.stuba.fei.uim.vsa.pr2.resources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {
    private Long aisId;
    private String name;
    private String email;
    private String password;
    private Integer year;
    private Integer term;
    private String programme;
}
