package restapi.demo;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Entity
@ToString
public class Movie {

    @Id
    @GeneratedValue
    public Long id;

    public String title;

    @OneToMany
    public Set<Actor> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

}
