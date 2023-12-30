package StripePaymentDemo.tax;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRateRepository extends MongoRepository<TaxRate,String> {

}
