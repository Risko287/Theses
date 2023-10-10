package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.solution.Student;
import sk.stuba.fei.uim.vsa.pr2.solution.ThesisService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("students")
public class StudentAPI {

    @Context
    SecurityContext securityContext;

    ThesisService thesisService = new ThesisService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        try {
            List<Student> students = thesisService.getStudents();
            //List<StudentResponse> studentResponseList = students.stream().map(student -> new StudentResponse(student.getAisId(), student.getAisId(), student.getName(), student.getEmail(), student.getYear(), student.getTerm(), student.getProgramme(), new ThesisResponse())).collect(Collectors.toList());
            List<StudentResponse> studentResponseList = students.stream()
                    .map(student -> new StudentResponse(student))
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(studentResponseList.toArray(new StudentResponse[0])).build();
            //return Response.ok().entity(students.toArray()).build();
        } catch (Exception e) {
            Message message = new Message(500, "Failed to get students", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") Long id) {
        try {
            Student student = thesisService.getStudent(id);
            StudentResponse studentResponse = new StudentResponse(student);
            return Response.status(Response.Status.OK).entity(studentResponse).build();
        } catch (Exception e) {
            Message message = new Message(500, "Failed to get student", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(CreateStudentRequest request) {
        try {
            Student student = thesisService.createStudent(request.getAisId(), request.getName(), request.getEmail(), request.getPassword(), request.getYear(), request.getTerm(), request.getProgramme());
            StudentResponse studentResponse = new StudentResponse(student.getAisId(), student.getAisId(), student.getName(), student.getEmail(), student.getYear(), student.getTerm(), student.getProgramme(), new ThesisResponse());
            return Response.created(null).entity(studentResponse).build();
        } catch (Exception e) {
            Message message = new Message(500, "Failed to create student", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
            return Response.serverError().entity(message).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") Long id) {
        try {
            Student student = thesisService.deleteStudent(id);
            StudentResponse studentResponse = new StudentResponse(student);
            return Response.status(Response.Status.OK).entity(studentResponse).build();
        } catch (Exception e) {
            Message message = new Message(500, "Failed to delete student", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }
}
