package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.solution.Student;
import sk.stuba.fei.uim.vsa.pr2.solution.Teacher;
import sk.stuba.fei.uim.vsa.pr2.solution.ThesisService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("teachers")
public class TeacherAPI {

    @Context
    SecurityContext securityContext;

    ThesisService thesisService = new ThesisService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers() {
        try {
            List<Teacher> teachers = thesisService.getTeachers();
            List<TeacherResponse> responseList = teachers.stream().map(teacher -> new TeacherResponse(teacher)).collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(responseList.toArray(new TeacherResponse[0])).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "No teachers found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to get teachers", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("id") Long id) {
        try {
            Teacher teacher = thesisService.getTeacher(id);
            //TeacherResponse teacherResponse = new TeacherResponse(teacher.getAisId(), teacher.getAisId(), teacher.getName(), teacher.getEmail(), teacher.getInstitute(), teacher.getDepartment(), new ArrayList<>());
            //teacherResponse.setTheses(teacher.getSupervisedTheses().stream().map(thesis -> new ThesisResponse(thesis.getId(), thesis.getTitle(), thesis.getStudent() != null ? thesis.getStudent().getId() : null, thesis.getStudent() != null ? thesis.getStudent().getName() : null)).collect(Collectors.toList()));

            //TeacherAltResponse alt = new TeacherAltResponse(teacher);
            TeacherResponse teacherResponse = new TeacherResponse(teacher);
            return Response.status(Response.Status.OK).entity(teacherResponse).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "Teacher not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to get teacher", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTeacher(CreateTeacherRequest teacherRequest) {
        try {
            Teacher teacher = thesisService.createTeacher(teacherRequest.getAisId(), teacherRequest.getName(), teacherRequest.getEmail(), teacherRequest.getPassword(), teacherRequest.getInstitute(), teacherRequest.getDepartment());
            TeacherResponse teacherResponse = new TeacherResponse(teacher.getAisId(), teacher.getAisId(), teacher.getName(), teacher.getEmail(), teacher.getInstitute(), teacher.getDepartment(), new ArrayList<>());
            return Response.status(Response.Status.CREATED).entity(teacherResponse).build();
        } catch (Exception e) {
            Message message = new Message(500, "Failed to create teacher", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("id") Long id) {
        try {
            Teacher teacher = thesisService.deleteTeacher(id);
            TeacherResponse teacherResponse = new TeacherResponse(teacher);

            return Response.status(Response.Status.OK).entity(teacherResponse).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "Teacher not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else if (e.getMessage().equals("Teacher has supervised theses")) {
                Message message = new Message(400, "Teacher has supervised theses", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to delete teacher", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

}
