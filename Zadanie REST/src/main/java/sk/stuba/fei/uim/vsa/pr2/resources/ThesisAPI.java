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
@Path("theses")
public class ThesisAPI {

    @Context
    SecurityContext securityContext;

    ThesisService thesisService = new ThesisService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTheses() {
        try {
            List<Thesis> theses = thesisService.getTheses();
            List<ThesisResponse> thesisResponseList = theses.stream().map(thesis -> new ThesisResponse(thesis)).collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(thesisResponseList.toArray(new ThesisResponse[0])).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "No theses found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to get theses", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThesis(@PathParam("id") Long id) {
        try {
            Thesis thesis = thesisService.getThesis(id);
            ThesisResponse thesisResponse = new ThesisResponse(thesis);
            return Response.status(Response.Status.OK).entity(thesisResponse).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "Thesis not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to get thesis", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createThesis(CreateThesisRequest createThesisRequest) {
        try {
            Thesis thesis = thesisService.makeThesisAssignment(158L, createThesisRequest.getTitle(), createThesisRequest.getType(), createThesisRequest.getDescription());
            ThesisResponse thesisResponse = new ThesisResponse(thesis);
            return Response.status(Response.Status.CREATED).entity(thesisResponse).build();
        } catch (Exception e) {
            Message message = new Message(500, "Failed to create thesis", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteThesis(@PathParam("id") Long id) {
        try {
            Thesis thesis = thesisService.deleteThesis(id);
            return Response.status(Response.Status.OK).entity(thesis).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "Thesis not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to delete thesis", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

    @POST
    @Path("{id}/assign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignThesis(@PathParam("id") Long id, Request assignThesisRequest) {
        try {
            Thesis thesis = thesisService.assignThesis(id, assignThesisRequest.getStudentId());
            ThesisResponse thesisResponse = new ThesisResponse(thesis);
            return Response.status(Response.Status.OK).entity(thesisResponse).build();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("NullPointerException")) {
                Message message = new Message(404, "Thesis not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }
            else if (e.getClass().getSimpleName().equals("IllegalArgumentException")) {
                Message message = new Message(400, "Student not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            }
            else if (e.getClass().getSimpleName().equals("IllegalStateException")) {
                Message message = new Message(400, "Thesis already assigned", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            }
            else {
                Message message = new Message(500, "Failed to assign thesis", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }
        }
    }

    @POST
    @Path("{id}/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitThesis(@PathParam("id") Long id, Request submitThesisRequest) {
        try {
            //TODO check if student is assigned to thesis
            //TODO check if student id is the same as the one in thesis author
            Thesis thesis = thesisService.submitThesis(id);
            ThesisResponse thesisResponse = new ThesisResponse(thesis);
            return Response.status(Response.Status.OK).entity(thesisResponse).build();
        } catch (Exception e) {
            switch (e.getClass().getSimpleName()) {
                case "NullPointerException": {
                    Message message = new Message(404, "Thesis not found", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                    return Response.status(Response.Status.NOT_FOUND).entity(message).build();
                }
                case "IllegalStateException": {
                    Message message = new Message(400, "Thesis already submitted", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                    return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
                }
                case "IllegalArgumentException": {
                    Message message = new Message(400, "Thesis not assigned", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                    return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
                }
                default: {
                    Message message = new Message(500, "Failed to submit thesis", new Error(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace())));
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
                }
            }
        }
    }
}
