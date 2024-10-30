package training.springcloud.order;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import training.springcloud.order.client.product.Product;
import training.springcloud.order.client.product.ProductClient;

import java.util.Map;

@SpringBootTest
class OrderServiceTest_MockedClients {

    @MockBean
    ProductClient productClient;

    @Autowired
    OrderService orderService;

    @Test
    void placeOrder() {
        // given
        Mockito.when(productClient.getProduct(Mockito.anyString()))
                .thenAnswer(i -> {
                    Product p = new Product(i.getArgument(0), "Mocked Product");
                    return p;
                });

        // when
        orderService.placeOrder(null, Map.of("p1", 1, "xyz", 50000));

        // then
        Mockito.verify(productClient, Mockito.times(2)).getProduct(Mockito.anyString());
    }
}