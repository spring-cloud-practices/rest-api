package client.domo;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Configuration
public class TraversonConfiguration {


    @Bean
    @Lazy
    Traverson traverson(RestTemplate restTemplate, ServletWebServerApplicationContext server) {
        URI baseUri = URI.create("http://localhost:" + server.getWebServer().getPort() + '/');

        Traverson traverson = new Traverson(baseUri, MediaTypes.HAL_JSON);
        traverson.setRestOperations(restTemplate);
        return traverson;
    }
}
