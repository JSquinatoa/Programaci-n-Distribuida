package customers.rest;


import customers.db.LineItem;
import customers.db.LineItemId;
import customers.repo.LineItemRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/lineitems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LineItemRest {

    @Inject
    private LineItemRepository lineItemRepository;

    @GET
    public List<LineItem> getAll (){
        return lineItemRepository.listAll();
    }

    // Cambiamos el path para recibir los dos componentes de la llave
    @GET
    @Path("/{orderId}/{idx}")
    public Response getById (@PathParam("orderId") Long orderId, @PathParam("idx") Integer idx){

        LineItemId id = new LineItemId();
        id.setOrder(orderId);
        id.setIdx(idx);

        return lineItemRepository.findByIdOptional(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}
