package restdocs.demo;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Daeho Oh
 * @since 2020-08-16
 */
@Getter
@Builder
public class Customer implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;

}
