package training.springcloud.order;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor

public class OrderRequest {

    private final String phoneNumber;
    private final Map<String, Integer> productQuantities;

}
