package com.wefox.demo.component;

import com.wefox.demo.exception.PaymentException;
import com.wefox.demo.service.LogService;
import com.wefox.demo.service.PaymentService;
import com.wefox.domain.dto.Error;
import com.wefox.domain.entity.Payment;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private PaymentService paymentService;

    private LogService logService;


    @KafkaListener(topics = "offline", groupId = "offlineGroup")
    public void consumerOfflinePayment(Payment payment){
        try {
            paymentService.createPayment(payment);
        } catch (PaymentException e) {
            logService.sendLog(e.getError());
        } catch (DataAccessException e) {
            logService.sendLog(new Error(payment.getPaymentId(), Error.ErrorType.database, e.getMessage()));
        } catch (RuntimeException e) {
            logService.sendLog(new Error(payment.getPaymentId(), Error.ErrorType.other, e.getMessage()));
        }
    }

    @KafkaListener(topics = "online", groupId = "onlineGroup")
    public void consumerOnlinePayment(Payment payment){
        try {
            if (paymentService.validateOnlinePayment(payment))
                paymentService.createPayment(payment);
        } catch (PaymentException e) {
            logService.sendLog(e.getError());
        } catch (DataAccessException e) {
            logService.sendLog(new Error(payment.getPaymentId(), Error.ErrorType.database, e.getMessage()));
        } catch (RuntimeException e) {
            logService.sendLog(new Error(payment.getPaymentId(), Error.ErrorType.other, e.getMessage()));
        }
    }
}