package books.config;

import books.clients.AuthorRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
//@COnfig es igual en sprgin
public class RestClientProducers {

    @Inject
    @ConfigProperty(name = "authors.url")
    String url;

    // Este tipo de produces es jakarta es que este compoente es un metoodo productor
    @Produces // en spring es el @BEan
    @ApplicationScoped // en spring no hace falta est porque todos los componetnes son singleton
    public AuthorRestClient authorRestClient(){
        return  RestClientBuilder.newBuilder()
                .baseUri(url) // Esta varailbe deveria está fuera del codigo fuente
                .build(AuthorRestClient.class);

    }


}
