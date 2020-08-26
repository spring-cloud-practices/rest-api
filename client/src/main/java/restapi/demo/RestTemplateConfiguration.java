package restapi.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
@Configuration
@Slf4j
public class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate() {

        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            log.info(String.format("request to URI %s with HTTP verb '%s'",
                    request.getURI(), Objects.requireNonNull(request.getMethod()).toString()));
            return execution.execute(request, body);
        };

        return new RestTemplateBuilder()
                .additionalInterceptors(interceptor)
                .build();
    }
}
