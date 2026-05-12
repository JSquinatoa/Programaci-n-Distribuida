package customers.rest;

import customers.db.Inventory;
import customers.repo.InventoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/invetories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryRest {

    @Inject
    private InventoryRepository inventoryRepository;

    @GET
    public List<Inventory> getall(){
        return inventoryRepository.listAll();
    }

    @GET
    @Path("/{bookIsbr}")
    public Response getById (@PathParam("bookIsbr") String bookIsbr){
        return inventoryRepository.findByIdOptional(bookIsbr)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();

    }

}
