package customers.rest;
import customers.clients.AuthorRestClient;
import customers.db.Book;
import customers.dtos.AuthorDto;
import customers.dtos.BookDto;
import customers.repo.BookRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Transactional
public class BookRest {

    @Inject
    private BookRepository bookRepository;


    AuthorRestClient client =  RestClientBuilder.newBuilder()
            .baseUri(String.valueOf(UriBuilder.fromUri("http://127.0.0.1:8070")))
            .build(AuthorRestClient .class);

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {
        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
                    // consultar los autores en hhttps 8070
//                    List<AuthorDto> autohrs = List.of(
//                            AuthorDto.builder()
//                                    .id(1)
//                                    .name("J.K. Rowling")
//                                    .build()
//                    );

                    var authors = client.findByBook(isbn);

                    return  BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .invetorySold(book.getInventory().getSold())
                            .invetorySupplied(book.getInventory().getSupplied())
                            .build();
                })
//                .map(it ->
//                        BookDto.builder()
//                                .isbn(it.getIsbn())
//                                .title(it.getTitle())
//                                .price(it.getPrice())
////                            .invetorySold(it.getInventory().getSold())
////                            .invetorySupplied(it.getInventory().getSold())
//                                .build())
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    public List<BookDto> findAll() {

        return bookRepository.streamAll()
                .map(it ->
                        BookDto.builder()
                                .isbn(it.getIsbn())
                                .title(it.getTitle())
                                .price(it.getPrice())
//                            .invetorySold(it.getInventory().getSold())
//                            .invetorySupplied(it.getInventory().getSold())
                                .build())
                .toList();

    }

    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, BookDto bookDto) {

        bookRepository.update(isbn, bookDto);
        return Response.ok().build();

    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn) {

        bookRepository.deleteById(isbn);
        return Response.ok().build();

    }

    @POST
    public Response delete(Book book) {
        bookRepository.persist(book);

        var uri = UriBuilder.fromUri("/books/{isbn}")
                .build(book.getIsbn());

        return Response.created(uri).build();

    }

}
