package training.springcloud.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Customer {

    private final long id;

    private final String name;

    private final String phoneNumber;

}
