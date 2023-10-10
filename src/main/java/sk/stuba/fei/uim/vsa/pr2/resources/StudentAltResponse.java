package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.vsa.pr2.solution.Student;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAltResponse {
    private Long id;
    private Long aisId;
    private String name;
    private String email;
    private Integer year;
    private Integer term;
    private String programme;
    private Long thesis;

    public StudentAltResponse(Student student){
        if (student == null) return;
        this.id = student.getAisId();
        this.aisId = student.getAisId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.year = student.getYear();
        this.term = student.getTerm();
        this.programme = student.getProgramme();
        if (student.getThesis() != null)
          this.thesis = student.getThesis().getId();
    }
}
