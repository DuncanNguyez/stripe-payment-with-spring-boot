package StripePaymentDemo.products;

import com.stripe.model.Price;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;


@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @MongoId
    private String id   ;
    private String stripeId;
    private String image;
    private String taxCode;
    private String name;
    private Long price;
    private List<String> stripePriceIds;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Transient
    private List<Price> stripePrices;
}
