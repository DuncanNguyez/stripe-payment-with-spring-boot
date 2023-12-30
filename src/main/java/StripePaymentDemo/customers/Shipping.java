package StripePaymentDemo.customers;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Shipping {
    private Address address;
    private String firstName;
    private String lastName;
    private String phone;
}
