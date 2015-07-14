package org.example.poc.dao;

import org.example.poc.model.Payment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by riby on 7/14/15.
 */
public interface PaymentRepository extends CrudRepository<Payment,Long> {

}
