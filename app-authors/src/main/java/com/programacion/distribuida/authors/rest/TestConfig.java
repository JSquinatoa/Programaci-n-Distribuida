package com.programacion.distribuida.authors.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path(("/config"))
public class TestConfig {

    @Inject
    @ConfigProperty (name = "quarkus.http.port", defaultValue = "8080")
    Integer port;


    @GET
    public String test(){

        Config config = ConfigProvider.getConfig();// Punto de entrada del sistema de configuración

        // listar las fuentes de configuración que tenemos disponibles
        config.getConfigSources().forEach(it -> {
            System.out.printf("[%d] \t %S \n", it.getOrdinal(), it.getName());
        });

        //-- Recuperar un valor desde la configuración
        String url = config.getValue("quarkus.datasource.jdbc.url", String.class);
        Integer puerto = config.getValue("quarkus.http.port", Integer.class);

        Optional<String> titulo = config.getOptionalValue("app.title", String.class);

        System.out.println("-------------------------------------");
        System.out.println("URL: " + url);
        System.out.println("puerto: " + puerto);
        if (titulo.isPresent()) {
            System.out.println("titulo: " + titulo.get());
        }else{
            System.out.println("title: no existe");
        }

        //-- Config más DI
        System.out.println("Puerto (DI): " + port);

        return "ok";
    }


}
