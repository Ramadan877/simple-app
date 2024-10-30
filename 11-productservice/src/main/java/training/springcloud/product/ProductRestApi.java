package training.springcloud.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductRestApi {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable String productId) {
        log.info("Received get-product request, productId={}", productId);
        return this.productService.getProduct(productId);
    }
}
