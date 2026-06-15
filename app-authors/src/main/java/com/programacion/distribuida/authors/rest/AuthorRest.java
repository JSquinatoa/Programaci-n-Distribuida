package com.programacion.distribuida.authors.rest;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.dtos.AuthorDto;
import com.programacion.distribuida.authors.repo.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer httpPort;

    AtomicInteger index = new AtomicInteger(1);

    @GET
    public List<Author> getAll(){
        return authorRepository.listAll()
                .stream()
                .peek(it -> it.setName(it.getName() + " - " + httpPort))
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response getById (@PathParam("id") Integer id){



        return  authorRepository.findByIdOptional(id)
                .map(it -> {
                    it.setName(it.getName() + " - " + httpPort);
                    return it;
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/find/{isbn}")
    public List<AuthorDto> findByBook(@PathParam("isbn") String isbn){

        int valor = index.getAndIncrement();
        if(valor % 5 == 1 || valor % 5 == 2 || valor % 5 == 3) {
            String msg = String.format("Intento %d, generando error", valor);
            System.out.println("author**********************************" + msg);
            throw new RuntimeException(msg);
        }

        return authorRepository.findbyBook(isbn)
                .stream()
                .peek(it -> it.setName(it.getName() + " - " + httpPort))
                .map(it -> AuthorDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build()

                )
                .toList();

    }
}
