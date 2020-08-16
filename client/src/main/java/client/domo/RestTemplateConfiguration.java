package client.domo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

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
                    request.getURI(), request.getMethod().toString()));
            return execution.execute(request, body);
        };

        return new RestTemplateBuilder()
                .additionalInterceptors(interceptor)
                .build();
    }
}
