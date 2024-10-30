package training.springcloud.order.client.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Product {

    private final String productId;

    private final String title;

}
