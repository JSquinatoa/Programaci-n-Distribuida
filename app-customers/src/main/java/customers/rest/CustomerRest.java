package customers.rest;

import customers.db.Customer;
import customers.repo.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerRest {

    @Inject
    private CustomerRepository customerRepository;

    @GET
    public List<Customer> getAll(){
        return customerRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById (@PathParam("id") Long id){
        return customerRepository.findByIdOptional(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

}
