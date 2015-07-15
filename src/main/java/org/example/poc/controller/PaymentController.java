package org.example.poc.controller;

import org.example.poc.dao.PaymentRepository;
import org.example.poc.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by azwickey on 7/14/15.
 */
@RestController
public class PaymentController {

    @Autowired
    private PaymentRepository repo;


    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public void hello(@RequestBody Payment payment) {
        //repo.save(new Payment(121L,"CreditCard","Ravneet","10201011","112","1234"));
        repo.save(payment);
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public Iterable<Payment> getPayments(){
        return repo.findAll();
    }


}
