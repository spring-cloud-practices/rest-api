package version.demo;

import lombok.Getter;

/**
 * @author Daeho Oh
 * @since 2020-08-16
 */
@Getter
public class Greeting {

    private String how;
    private String version;

    public Greeting(String how, ApiVersion version) {
        this.how = how;
        this.version = version.toString();
    }
}
