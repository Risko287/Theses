package sk.stuba.fei.uim.vsa.pr2.resources;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
@Slf4j
@Path("hello")
public class HelloResource {
    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello unknown user!";
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(@PathParam("name") String name) {
        return Response.accepted().entity("Hello " + name + "!").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String helloFromBody(CreateStudentRequest request) {
        return "Hello " + (request.getName() == null || request.getName().isEmpty() ? "unknown user" : request.getName()) + "!";
        //return "Hello " + (name == null || name.isEmpty() ? "unknown user" : name) + "!";
    }
}