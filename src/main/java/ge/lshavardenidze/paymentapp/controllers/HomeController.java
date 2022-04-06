package ge.lshavardenidze.paymentapp.controllers;

import ge.lshavardenidze.paymentapp.dtos.PaymentTransactionDTO;
import ge.lshavardenidze.paymentapp.exception.PaymentException;
import ge.lshavardenidze.paymentapp.servicies.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "")
public class HomeController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/")
    public ModelAndView goToHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customerDataAngularJS.html");
        return modelAndView;
    }

    @PostMapping(value = "/save-payment")
    public void savePayment(@Validated @RequestBody PaymentTransactionDTO payment) throws PaymentException {
        System.out.println(payment);
        paymentService.savePayment(payment);
    }

}
