package StripePaymentDemo.tax;

import com.stripe.param.TaxRateCreateParams;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxRate {
    @MongoId
    private String id;
    private String country;
    private String description;
    private String displayName;
    private String stripeId;
    private Long percentage;
    private String state;
    private TaxRateCreateParams.TaxType type;
}
