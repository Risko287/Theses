package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.vsa.pr2.solution.Thesis;
import sk.stuba.fei.uim.vsa.pr2.solution.ThesisStatus;
import sk.stuba.fei.uim.vsa.pr2.solution.ThesisType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThesisResponse {
    private Long id;
    private String registrationNumber;
    private String title;
    private String description;
    private String department;
    private TeacherAltResponse supervisor;
    private StudentAltResponse author;
    private String publishedOn;
    private String deadline;
    private ThesisType type;
    private ThesisStatus status;

    public ThesisResponse(Thesis thesis){
        if (thesis != null) {
            this.id = thesis.getId();
            this.registrationNumber = thesis.getRegistrationNumber();
            this.title = thesis.getTitle();
            this.description = thesis.getDescription();
            this.department = thesis.getDepartment();
            this.supervisor = new TeacherAltResponse(thesis.getSupervisor());
            this.author = new StudentAltResponse(thesis.getAuthor());
            this.publishedOn = thesis.getPublishedOn().toString();
            this.deadline = thesis.getDeadline().toString();
            this.type = thesis.getType();
            this.status = thesis.getStatus();
        }
    }
}

