package customers.clients;

import customers.dtos.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.util.List;


@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthorRestClient {

    /**
     * podemos usar jasrxclient
     * miropifile client
     * el htpp
     * la idea del micropofile es tener una terface que simule
     * la itnerface del servidor
     *
     * */


    @GET
    @Path("/find/{isbn}")
    public List<AuthorDto> findByBook(@PathParam("isbn") String isbn);

}
