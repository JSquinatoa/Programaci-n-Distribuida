package customers.rest;

import customers.db.PurchaseOrder;
import customers.repo.PurchaseOrderRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/purchaseorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseOrderRest {

    @Inject
    private PurchaseOrderRepository purchaseOrderRepository;

    @GET
    public List<PurchaseOrder> getAll (){
        return purchaseOrderRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById (@PathParam("id") Long id){
        return purchaseOrderRepository.findByIdOptional(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

}
