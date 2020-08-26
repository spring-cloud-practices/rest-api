package client.demo;

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
@NoArgsConstructor
@ToString(exclude = {"movie"})
public class Actor {

    @Id
    @GeneratedValue
    public Long id;

    public String fullName;

    @ManyToOne
    public Movie movie;

    public Actor(String fullName, Movie movie) {
        this.fullName = fullName;
        this.movie = movie;
    }
}
