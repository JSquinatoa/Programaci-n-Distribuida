package books;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;

@ApplicationScoped
public class BooksLifeCicle  {

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer appPort;


    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    String serviceId;

    public void init (@Observes StartupEvent event, Vertx vertx){
        System.out.println("books-lifecycle: init");

        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            serviceId = "app-books-%s:%d".formatted(ipAddress, appPort);

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-books")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort);

            client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("Books service registered in Consul ID: " + serviceId))
                    .onFailure(it -> System.out.println("Failed to register Books service in Consult "+ it.getMessage()));;

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void destroy (@Observes ShutdownEvent event, Vertx vertx){
        System.out.println("books-lifecycle: destroy");

        ConsulClientOptions options = new ConsulClientOptions()
                .setHost(consulHost)
                .setPort(consulPort);

        ConsulClient client = ConsulClient.create(vertx, options);

        client.deregisterService(serviceId);

        client.deregisterService(serviceId)
                .onSuccess(it -> System.out.println("Books service deregistered from cConsul with ID: " + serviceId))
                .onFailure(it -> System.out.println("Failed to deregister Books service from consul "+ it.getMessage()));

    }
}
