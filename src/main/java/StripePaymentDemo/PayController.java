package StripePaymentDemo;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PayController {
    @GetMapping("/pay")
    public String pay() {
        return "pay";
    }

    @GetMapping("/success")
    public String success(@RequestParam("id") String id, Model model) throws StripeException {
        if (id != null) {
            Session session = Session.retrieve(id);
            model.addAttribute("data", session);
        }
        return "success";
    }
}
