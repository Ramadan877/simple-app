package training.springcloud.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import training.springcloud.order.client.customer.CustomerClient;
import training.springcloud.order.client.product.ProductClient;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final ProductClient productClient;

    private final CustomerClient customerClient;

    private final Tracer tracer;

    @NewSpan
    public Order placeOrder(String mobilePhoneNumber, Map<String, Integer> productQuantities) {
        log.info("placeOrder({}, {})", mobilePhoneNumber, productQuantities);

        // products
        var currentSpan = tracer.currentSpan();
        var products = productQuantities.keySet().parallelStream()
                .map(
                        productId -> {
                            tracer.withSpan(currentSpan);
                            return productClient.getProduct(productId);
                        })
                .collect(Collectors.toList());
        log.info("Collected {} product(s)", products.size());

        // customer
        var customer = customerClient.getCustomers(mobilePhoneNumber);
        log.info("Got customer with name '{}'", customer.getName());

        return null;
    }

}

