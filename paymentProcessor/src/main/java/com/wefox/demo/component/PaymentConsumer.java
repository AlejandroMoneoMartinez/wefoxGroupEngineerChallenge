package com.wefox.demo.component;

import com.wefox.domain.entity.Payment;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private static final Log LOG = LogFactory.getLog(PaymentConsumer.class);


    @KafkaListener(topics = "offline", groupId = "offlineGroup")
    public void consumerOfflinePayment(Payment payment){
        LOG.info(payment);
    }

    @KafkaListener(topics = "online", groupId = "onlineGroup")
    public void consumerOnlinePayment(Payment payment){
        LOG.info(payment);
    }
}
