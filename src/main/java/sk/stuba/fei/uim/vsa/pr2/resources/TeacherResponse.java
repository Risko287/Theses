package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.vsa.pr2.solution.Teacher;
import sk.stuba.fei.uim.vsa.pr2.solution.Thesis;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {
    private Long id;
    private Long aisId;
    private String name;
    private String email;
    private String institute;
    private String department;
    private List<ThesisResponse> theses;

    public TeacherResponse (Teacher teacher) {
        this.id = teacher.getAisId();
        this.aisId = teacher.getAisId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.institute = teacher.getInstitute();
        this.department = teacher.getDepartment();
        theses = new ArrayList<>();
        for (Thesis thesis : teacher.getSupervisedTheses()) {
            this.theses.add(new ThesisResponse(thesis));
        }
    }
}
