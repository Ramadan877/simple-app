package training.springcloud.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestApi {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public Customer getCustomers(@RequestParam String phoneNumber) {
        log.info("Received get-customers request, phoneNumber={}", phoneNumber);
        return this.customerService.getCustomers(phoneNumber);
    }
}
