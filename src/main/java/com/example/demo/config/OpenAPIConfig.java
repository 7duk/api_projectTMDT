package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    private  String devUrl = "http://api.shopiec.shop";
    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("ducnguyen.12965@gmail.com");
        contact.setName("Duc Nguyen");
        contact.setUrl("");


        Info info = new Info()
                .title("Demo Service API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage demo.")
                ;
        return new OpenAPI().info(info).servers(List.of(devServer));

    }
}



