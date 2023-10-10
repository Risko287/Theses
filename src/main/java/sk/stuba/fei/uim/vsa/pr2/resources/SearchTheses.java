package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.solution.Student;
import sk.stuba.fei.uim.vsa.pr2.solution.Teacher;
import sk.stuba.fei.uim.vsa.pr2.solution.Thesis;
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
@Path("search/theses")
public class SearchTheses {

    @Context
    SecurityContext securityContext;

    ThesisService thesisService = new ThesisService();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTheses(Request request) {
        try {
            Thesis thesis = thesisService.getThesisByStudent(request.getStudentId());
            ThesisResponse thesisResponse = new ThesisResponse(thesis);
            return Response.status(Response.Status.OK).entity(thesisResponse).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "Thesis not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            } else {
                Message message = new Message(500, "Failed to assign thesis", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }

        }
    }
}
