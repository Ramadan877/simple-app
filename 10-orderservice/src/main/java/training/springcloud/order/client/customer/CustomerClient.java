package training.springcloud.order.client.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import training.springcloud.order.client.product.Product;

@FeignClient(name = "customerservice", fallback = CustomerClientFallback.class)
public interface CustomerClient {

    @GetMapping("/customers")
    Customer getCustomers(@RequestParam String phoneNumber);

}

@Component
class CustomerClientFallback implements CustomerClient {
    @Override
    public Customer getCustomers(String phoneNumber) {
        return new Customer(0, null, phoneNumber);
    }
}
