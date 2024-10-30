package training.springcloud.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    @NewSpan
    public Product getProduct(String productId) {
        log.info("getProduct({})", productId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        return new Product(productId, "Some random title");
    }

}
