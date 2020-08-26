package restapi.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"movie"})
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    @ManyToOne
    private Movie movie;

    public Actor(String fullName, Movie movie) {
        this.fullName = fullName;
        this.movie = movie;
    }
}
