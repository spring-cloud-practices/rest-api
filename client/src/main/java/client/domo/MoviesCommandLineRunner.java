package client.domo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MoviesCommandLineRunner implements CommandLineRunner {

    private final TransactionTemplate transactionTemplate;

    private final ActorRepository actorRepository;

    private final MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Movie> movies = transactionTemplate.execute(tx ->
                Stream.of("Cars (Owen Wilson,Paul Newman,Bonnie Hunt)",
                        "Batman (Michael Keaton,Jack Nicholson)",
                        "Lost in Translation (Bill Murray)")
                        .map(String::trim)
                        .map(i -> {
                            Matcher matcher = Pattern.compile("(.*?)\\s*?\\((.*?)\\)").matcher(i);
                            Assert.state(matcher.matches(), "[Assertion failed] - this state invariant must be true");
                            Movie movie = movieRepository.save(new Movie(matcher.group(1)));
                            Arrays.stream(matcher.group(2).split(",")).map(String::trim).forEach(a -> {
                                Actor actor = actorRepository.save(new Actor(a.trim(), movie));
                                movie.actors.add(actorRepository.getOne(actor.id));
                                movieRepository.save(movie);
                            });
                            return movieRepository.getOne(movie.id);
                        })
                        .collect(Collectors.toList()));

        movies.forEach(m -> log.info(m.toString()));
    }
}
