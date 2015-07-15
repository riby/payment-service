package org.example.poc.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.example.poc.dao.PaymentRepository;
import org.example.poc.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;


/**
 * Created by azwickey on 7/14/15.
 */
@RestController
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentRepository _repo;
    @Autowired
    private RabbitTemplate _template;


    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public void makePayment(@RequestBody Payment payment) {
        _repo.save(payment);
        sendPaymentConf(payment);
        LOG.info("Payment sent");
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public Iterable<Payment> getPayments(){
        return _repo.findAll();
    }

    @HystrixCommand(fallbackMethod="unhealthy")
    @RequestMapping("/circuitBreakerTest")
    public String circuitBreaker() {
        if (Calendar.getInstance().get(Calendar.MINUTE) % 2 == 0) {
            throw new RuntimeException();
        }
        return "HEALTHY!";
    }

    protected String unhealthy() {
        return "UNHEALTHY.";
    }

    protected void sendPaymentConf(Payment p){
        _template.convertAndSend(p);
    }

}
