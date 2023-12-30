package StripePaymentDemo.customers;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address {
    private String city;
    private String country;
    private String line1;
    private String line2;
    private String postalCode;
    private String state;
}
