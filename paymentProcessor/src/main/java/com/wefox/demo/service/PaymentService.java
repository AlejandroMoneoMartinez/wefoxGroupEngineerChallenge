package com.wefox.demo.service;

import com.wefox.demo.exception.PaymentException;
import com.wefox.domain.dto.Error;
import com.wefox.domain.entity.Account;
import com.wefox.domain.entity.Payment;
import com.wefox.repository.jpaRepository.AccountRepository;
import com.wefox.repository.jpaRepository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    private AccountRepository accountRepository;


    public PaymentService(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(rollbackFor={Exception.class})
    public Payment createPayment(Payment payment) throws PaymentException {

        Account accountDb = accountRepository.findById(payment.getAccount().getAccountId()).orElseThrow(()
                -> new PaymentException(new Error(payment.getPaymentId(), Error.ErrorType.database,
                "Account with id " + payment.getAccount().getAccountId() + " not found")));

        payment.setCreatedOn(LocalDateTime.now());
        accountDb.setLastPaymentDate(payment.getCreatedOn());
        accountRepository.save(accountDb);
        return paymentRepository.save(payment);
    }
}
