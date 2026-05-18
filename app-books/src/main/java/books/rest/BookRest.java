package books.rest;
import books.clients.AuthorRestClient;
import books.db.Book;
import books.dtos.BookDto;
import books.repo.BookRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

//Pring REquestaMApped y RequesCOntroller
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RequiredArgsConstructor
@Transactional
@ApplicationScoped
public class BookRest {

//    @Inject En Spring es atuowired y la inyecion de dependcia por constructor funciona exatmente igual
//    private BookRepository bookRepository;

    //Segunda oopcion utilizar un poducto CDI
//    @Inject
//    AuthorRestClient client;

//    // inyecion de dependecia por constructor
//
        // normalito
//    @Inject
//    BookRepository bookRepository;
//
//    @Inject
//    @RestClient
//    AuthorRestClient client;

    // por eo le pongo final porque eso e obliga a incializarlsas en el costructor y esto
    // es inuyeccion de depencia por consutructor

    // injecion porconsutrctor
    // en quarkus no neeistmoas ponerl en @Inject
//    public BookRest(BookRepository bookRepository, AuthorRestClient client) {
//        this.bookRepository = bookRepository;
//        this.client = client;
//    }
    //y si no pues usamos la anotaicó de REquiredConstructor, es de lombok, todas las final se van
    // inicalicar po todas las que estan ahi, y el @inject no se lo usa es la versión antigua


    // opciones para crear primero
//    @PostConstruct
//    public void init (){
//        client =  RestClientBuilder.newBuilder()
//                .baseUri("http://localhost:8080")
//                .build(AuthorRestClient.class);
//
//    }

    // hacerlo por consturctor
//    @Inject
    final BookRepository bookRepository;

//    @Inject
//    @RestClient
    final AuthorRestClient client;

    @Inject
    public BookRest(BookRepository bookRepository,  @RestClient AuthorRestClient client) {
        this.bookRepository = bookRepository;
        this.client = client;
    }

    //@Getmapping   en SPring
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
                .map(book -> {
                    // consultar los autores en hhttps 8070
//                    List<AuthorDto> autohrs = List.of(
//                            AuthorDto.builder()
//                                    .id(1)
//                                    .name("J.K. Rowling")
//                                    .build()
//                    );

                    var authors = client.findByBook(book.getIsbn());

                    return  BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .invetorySold(book.getInventory().getSold())
                            .invetorySupplied(book.getInventory().getSupplied())
                            .build();
                })
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
