package org.example.poc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by azwickey on 7/14/15.
 */
@RestController
public class PaymentController {

    @RequestMapping("/hello")
    public String hello(String message) {
        return "hello " + message;
    }


}
