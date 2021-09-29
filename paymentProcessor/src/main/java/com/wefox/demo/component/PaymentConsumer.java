package com.wefox.demo.component;

import com.wefox.demo.exception.PaymentException;
import com.wefox.demo.service.PaymentService;
import com.wefox.domain.dto.Error;
import com.wefox.domain.entity.Payment;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private static final Log LOG = LogFactory.getLog(PaymentConsumer.class);

    private PaymentService paymentService;


    @KafkaListener(topics = "offline", groupId = "offlineGroup")
    public void consumerOfflinePayment(Payment payment) {
        try {
            paymentService.createPayment(payment);
        } catch (PaymentException e) {
            LOG.error(e.getError());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "online", groupId = "onlineGroup")
    public void consumerOnlinePayment(Payment payment){
        try {
            if (paymentService.validateOnlinePayment(payment))
                paymentService.createPayment(payment);
        } catch (PaymentException e) {
            LOG.error(e.getError());
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }
}
