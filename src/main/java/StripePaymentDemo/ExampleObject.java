package StripePaymentDemo;

import StripePaymentDemo.customers.Address;
import StripePaymentDemo.customers.Customer;
import StripePaymentDemo.customers.CustomerRepository;
import StripePaymentDemo.customers.Shipping;
import StripePaymentDemo.products.Product;
import StripePaymentDemo.products.ProductRepository;
import StripePaymentDemo.tax.TaxRate;
import StripePaymentDemo.tax.TaxRateRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.TaxRateCreateParams;
import com.stripe.param.common.EmptyParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class ExampleObject {
    @Value("${application.stripe.api-key}")
    private String stripeApiKey;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    TaxRateRepository taxRateRepository;
    @Autowired
    ProductRepository productRepository;

    private ExampleObject() {
    }

    @Transactional
    public void init() throws StripeException {
        Stripe.apiKey = stripeApiKey;
        if (productRepository.findAll().isEmpty()) initProducts();
        if (customerRepository.findAll().isEmpty()) initCustomers();
        if (taxRateRepository.findAll().isEmpty()) initTaxRates();
    }

    private void initTaxRates() throws StripeException {
        List<TaxRate> taxRates = new ArrayList<>();

        taxRates.add(taxRateRepository.save(TaxRate.builder()
                .type(TaxRateCreateParams.TaxType.VAT)
                .country("US")
                .state("CA")
                .description("CA sales tax")
                .percentage(10d)
                .displayName("Sales tax")
                .build()
        ));taxRates.add(taxRateRepository.save(TaxRate.builder()
                .type(TaxRateCreateParams.TaxType.VAT)
                .country("US")
                .state("AL")
                .description("AL sales tax")
                .percentage(9.33d)
                .displayName("Sales tax")
                .build()
        ));taxRates.add(taxRateRepository.save(TaxRate.builder()
                .type(TaxRateCreateParams.TaxType.VAT)
                .country("US")
                .state("CO")
                .description("CO sales tax")
                .percentage(8.93d)
                .displayName("Sales tax")
                .build()
        ));
        for (TaxRate taxRate : taxRates) {
            initStripeTaxRate(taxRate);
        }

    }

    private void initStripeTaxRate(TaxRate taxRate) throws StripeException {
        TaxRateCreateParams params = TaxRateCreateParams.builder()
                .setCountry(taxRate.getCountry())
                .setState(taxRate.getState())
                .setTaxType(taxRate.getType())
                .setDisplayName(taxRate.getDisplayName())
                .setPercentage(BigDecimal.valueOf(taxRate.getPercentage()))
                .setDescription(taxRate.getDescription())
                .setInclusive(false)
                .build();
        com.stripe.model.TaxRate stripeTaxRate = com.stripe.model.TaxRate.create(params);
        taxRate.setStripeId(stripeTaxRate.getId());
        taxRateRepository.save(taxRate);
    }

    private void initProducts() throws StripeException {
        List<Product> products = new ArrayList<>();
        products.add(productRepository.save(Product.builder()
                .name("Product_1")
                .price(123L)
                .image("https://pawfecthouse.com/cdn/shop/files/thumb-1_4c93c84a-651b-42a0-a4b9-5acf87021e97_5000x.jpg?v=1701250139")
                .build())
        );
        products.add(productRepository.save(Product.builder()
                .name("Product_2")
                .price(38L)
                .image("https://pawfecthouse.com/cdn/shop/files/thumb-1_4c93c84a-651b-42a0-a4b9-5acf87021e97_5000x.jpg?v=1701250139")
                .build())
        );
        products.add(productRepository.save(Product.builder()
                .name("Product_3")
                .price(311L)
                .image("https://pawfecthouse.com/cdn/shop/files/thumb-1_4c93c84a-651b-42a0-a4b9-5acf87021e97_5000x.jpg?v=1701250139")
                .build())
        );
        for (Product product : products) {
            initStripeProduct(product);
        }

    }

    private void initCustomers() throws StripeException {
        List<Customer> customers = new ArrayList<>();
        customers.add(
                customerRepository.save(Customer.builder()
                        .address(Address.builder()
                                .city("Vinh")
                                .country("VN")
                                .state("Nghe An")
                                .line1("Nam Thai commune, Nam Dan district")
                                .build()
                        )
                        .shipping(Shipping.builder()
                                .address(Address.builder()
                                        .city("Vinh")
                                        .country("VN")
                                        .state("Nghe An")
                                        .line1("Nam Thai commune, Nam Dan district")
                                        .build()
                                )
                                .firstName("firstName")
                                .lastName("lastName_1")
                                .phone("0835666356")
                                .build())
                        .firstName("firstName")
                        .lastName("lastName_1")
                        .phone("0835666356")
                        .build())
        );
        customers.add(
                customerRepository.save(Customer.builder()
                        .address(Address.builder()
                                .city("Seattle")
                                .country("VN")
                                .state("Washington")
                                .line1("1005 6th Ave Parking")
                                .build()
                        )
                        .shipping(Shipping.builder()
                                .address(Address.builder()
                                        .city("Seattle")
                                        .country("VN")
                                        .state("Washington")
                                        .line1("1005 6th Ave Parking")
                                        .build()
                                )
                                .firstName("firstName")
                                .lastName("lastName_2")
                                .phone("0835666356")
                                .build())
                        .firstName("firstName")
                        .lastName("lastName_2")
                        .phone("0835666356")
                        .build())
        );
        for (Customer customer : customers) {
            initStripeCustomer(customer);
        }
    }

    private void initStripeProduct(Product product) throws StripeException {
        ProductCreateParams params = ProductCreateParams.builder()
                .setName(product.getName())
                .addImage(product.getId())
                .putMetadata("id", product.getId())
                .setUrl("https://pawfecthouse.com/products/eat-drink-and-be-merry-dog-personalized-custom-mug-christmas-gift-for-pet-owners-pet-lovers?variant=45134204993768")
                .build();
        com.stripe.model.Product stripeProduct = com.stripe.model.Product.create(params);
        product.setStripeId(stripeProduct.getId());
        PriceCreateParams priceCreateParams = PriceCreateParams.builder()
                .setCurrency("usd")
                .setUnitAmount(product.getPrice())
                .setProduct(stripeProduct.getId())
                .build();
        Price price = Price.create(priceCreateParams);
        List<String> priceIds = new ArrayList<>();
        priceIds.add(price.getId());
        product.setStripePriceIds(priceIds);
        productRepository.save(product);
    }

    private void initStripeCustomer(Customer customer) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(customer.getEmail())
                .setName(customer.getFirstName() + " " + customer.getLastName())
                .setMetadata((EmptyParam) new HashMap<>().put("id", customer.getId()))
                .setPhone(customer.getPhone())
                .setAddress(CustomerCreateParams.Address.builder()
                        .setCity(customer.getAddress().getCity())
                        .setCountry(customer.getAddress().getCountry())
                        .setState(customer.getAddress().getState())
                        .setLine1(customer.getAddress().getLine1())
                        .build()
                )
                .setShipping(CustomerCreateParams.Shipping.builder()
                        .setName(customer.getFirstName() + " " + customer.getLastName())
                        .setPhone(customer.getPhone())
                        .setAddress(CustomerCreateParams.Shipping.Address.builder()
                                .setCity(customer.getShipping().getAddress().getCity())
                                .setCountry(customer.getShipping().getAddress().getCountry())
                                .setLine1(customer.getShipping().getAddress().getLine1())
                                .setState(customer.getShipping().getAddress().getState())
                                .build())
                        .build())
                .build();
        com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.create(params);
        customer.setStripeId(stripeCustomer.getId());
        customerRepository.save(customer);
    }

}
