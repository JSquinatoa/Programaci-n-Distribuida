package customers.rest;

import customers.db.Book;
import customers.repo.BookRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {

    @Inject
    private BookRepository bookRepository;

    @GET
    public List<Book> getAll() {
        return bookRepository.listAll();
    }

    @GET
    @Path("/{isbn}")
    public Response getById (@PathParam("isbn") String isbn){
        return bookRepository.findByIdOptional(isbn)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

}
