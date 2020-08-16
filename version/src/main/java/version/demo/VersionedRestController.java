package version.demo;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Daeho Oh
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/api")
public class VersionedRestController {

    private static final String V1_MEDIA_TYPE_VALUE = "application/vnd.bootiful.demo-v1+json";
    private static final String V2_MEDIA_TYPE_VALUE = "application/vnd.bootiful.demo-v2+json";

    @GetMapping(value = "/{version}/hi", produces = APPLICATION_JSON_VALUE)
    public Greeting greetingWithPathVariable(@PathVariable ApiVersion version) {
        return greet(version, "path-variable");
    }

    @GetMapping(value = "/hi", produces = APPLICATION_JSON_VALUE)
    public Greeting greetWithHeader(@RequestHeader("X-API-Version") ApiVersion version) {
        return greet(version, "header");
    }

    @GetMapping(value = "/hi", produces = V1_MEDIA_TYPE_VALUE)
    public Greeting greetWithContentNegotiationV1() {
        return greet(ApiVersion.v1, "content-negotiation");
    }

    @GetMapping(value = "/hi", produces = V2_MEDIA_TYPE_VALUE)
    public Greeting greetWithContentNegotiationV2() {
        return greet(ApiVersion.v2, "content-negotiation");
    }

    private Greeting greet(ApiVersion version, String how) {
        return new Greeting(how, version);
    }

}
