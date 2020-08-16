package client.domo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@RepositoryRestResource
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
