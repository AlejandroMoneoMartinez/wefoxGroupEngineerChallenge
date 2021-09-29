package com.wefox.repository.jpaRepository;

import com.wefox.domain.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {
}