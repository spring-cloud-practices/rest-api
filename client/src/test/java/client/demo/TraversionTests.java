package client.demo;

import client.domo.Actor;
import client.domo.ClientApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Slf4j
public class TraversionTests {

    private ConfigurableApplicationContext server;

    private Traverson traverson;

    @BeforeEach
    public void setUp() throws Exception {

        server = new SpringApplicationBuilder()
                .properties(Collections.singletonMap("server.port", "0"))
                .sources(ClientApplication.class)
                .run();
        // server = SpringApplication.run(ClientApplication.class);
        traverson = server.getBean(Traverson.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (null != server) {
            server.close();
        }
    }

    @Test
    public void tearTraverson() throws Exception {

        String nameOfMovie = "Cars";

        // <1>
        CollectionModel<Actor> actorCollectionModel = traverson
                .follow("actors", "search", "by-movie")
                .withTemplateParameters(Collections.singletonMap("movie", nameOfMovie))
                .toObject(new ParameterizedTypeReference<>() {});

        assert actorCollectionModel != null;
        actorCollectionModel.forEach(a -> log.info(a.toString()));
        Assertions.assertTrue(actorCollectionModel.getContent().size() > 0);
        Assertions.assertEquals(
                (int) actorCollectionModel.getContent().stream()
                        .filter(a -> a.fullName.equals("Owen Wilson")).count(), 1
        );
    }
}
