package restdocs.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daeho Oh
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @GetMapping("/v1/customers")
    public ResponseEntity<List<Customer>> getCollection() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(
                    Customer.builder()
                            .id((long) i)
                            .firstName("firstName" + i)
                            .lastName("lastName" + i)
                            .build()
            );
        }
        return ResponseEntity.ok(customers);
    }
}
