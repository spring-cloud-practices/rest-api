package client.demo;

import client.domo.ClientApplication;
import client.domo.Movie;
import client.domo.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Slf4j
public class RestTemplateTests {

    private ConfigurableApplicationContext server;

    private RestTemplate restTemplate;

    private MovieRepository movieRepository;

    private URI moviesUri;

    @BeforeEach
    public void setUp() throws Exception {

        server = new SpringApplicationBuilder()
                .properties(Collections.singletonMap("server.port", "0"))
                .sources(ClientApplication.class)
                .run();

        int port = server.getEnvironment().getProperty("local.server.port", Integer.class, 8080);

        restTemplate = server.getBean(RestTemplate.class);
        URI baseUri = URI.create("http://localhost:" + port + "/");
        moviesUri = URI.create(baseUri.toString() + "movies");
        movieRepository = server.getBean(MovieRepository.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (null != server) {
            server.close();
        }
    }

    @Test
    public void testRestTemplate() throws Exception {
        // <1>
        ResponseEntity<Movie> postMovieResponseEntity =
                restTemplate.postForEntity(moviesUri, new Movie("Forest Gump"), Movie.class);
        URI uriOfNewMovie = postMovieResponseEntity.getHeaders().getLocation();
        log.info("the new movie lives at " + uriOfNewMovie);

        // <2>
        assert uriOfNewMovie != null;
        JsonNode mapForMovieRecord = restTemplate.getForObject(uriOfNewMovie, JsonNode.class);
        log.info("\t..read as a Map.class: " + mapForMovieRecord);
        assert mapForMovieRecord != null;
        Assertions.assertEquals(mapForMovieRecord.get("title").asText(), Objects.requireNonNull(postMovieResponseEntity.getBody()).title);

        // <3>
        Movie movieReference = restTemplate.getForObject(uriOfNewMovie, Movie.class);
        assert movieReference != null;
        Assertions.assertEquals(movieReference.title, postMovieResponseEntity.getBody().title);
        log.info("\t..read as a Movie.class: " + movieReference);

        // <4>
        ResponseEntity<Movie> movieResponseEntity = restTemplate.getForEntity(uriOfNewMovie, Movie.class);
        Assertions.assertEquals(movieResponseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(movieResponseEntity.getHeaders().getContentType(),
                MediaType.parseMediaType("application/json"));
        log.info("\t..read as a ResponseEntity<Movie>: " + movieResponseEntity);

        // <5>
        ParameterizedTypeReference<CollectionModel<Movie>> movies = new ParameterizedTypeReference<>() {};
        ResponseEntity<CollectionModel<Movie>> moviesResponseEntity =
                restTemplate.exchange(moviesUri, HttpMethod.GET, null, movies);
        CollectionModel<Movie> movieCollectionModel = moviesResponseEntity.getBody();
        Objects.requireNonNull(movieCollectionModel).forEach(m -> log.info(m.toString()));
        Assertions.assertEquals(movieCollectionModel.getContent().size(), movieRepository.count());
        Assertions.assertEquals(movieCollectionModel.getLinks().stream()
                .filter(m -> m.getRel().toString().equals("self")).count(), 1);
    }

}
