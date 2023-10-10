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
public class TeacherAltResponse {
    private Long id;
    private Long aisId;
    private String name;
    private String email;
    private String institute;
    private String department;
    private List<Long> theses;

    public TeacherAltResponse(Teacher teacher){
        this.id = teacher.getAisId();
        this.aisId = teacher.getAisId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.institute = teacher.getInstitute();
        this.department = teacher.getDepartment();
        //this.theses = teacher.getSupervisedTheses();
        theses = new ArrayList<>();
        for (Thesis thesis : teacher.getSupervisedTheses()) {
            this.theses.add(thesis.getId());
        }
    }
}
