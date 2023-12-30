package StripePaymentDemo.checkouts;

import StripePaymentDemo.customers.CustomerRepository;
import StripePaymentDemo.products.ProductRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout-session")
@Slf4j
public class CheckoutController {

    @Value("${application.stripe.api-key}")
    private String stripeApikey;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/create")
    public String create() throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("https://localhost/success?id={CHECKOUT_SESSION_ID}")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(productRepository.findAll().get(0).getStripePriceIds().get(0))
                                        .setQuantity(2L)
                                        .build()
                        )
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(productRepository.findAll().get(1).getStripePriceIds().get(0))
                                        .setQuantity(2L)
                                        .build()
                        )
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(productRepository.findAll().get(2).getStripePriceIds().get(0))
                                        .setQuantity(2L)
                                        .build()
                        )
                        .setAutomaticTax(SessionCreateParams.AutomaticTax.builder().setEnabled(true).build())
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customerRepository.findAll().get(0).getStripeId())
                        .build();
        Session session = Session.create(params);

        log.info("session: {}", session);
        return "redirect:" + session.getUrl();
    }
}
