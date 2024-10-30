package training.springcloud.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

    @NewSpan
    public Customer getCustomers(String phoneNumber) {
        log.info("getCustomers({})", phoneNumber);
        int faultWindowSeconds = 10;
        boolean isFaulty = ((System.currentTimeMillis() / 1000) % faultWindowSeconds * 2) <= faultWindowSeconds;
        if (isFaulty) {
            throw new IllegalStateException("currently faulty");
        }
        else {
            return new Customer(123L, "Some Dude", phoneNumber);
        }
    }

}
