package com.wefox.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {

    public enum PaymentType {
        online,
        offline;
    }

    @Id
    @JsonProperty("payment_id")
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonUnwrapped
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(length = 150, nullable = false)
    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @Column(length = 100)
    @JsonProperty("credit_card")
    private String creditCard;

    @Column(nullable = false)
    private BigDecimal amount;

    @JsonProperty("created_at")
    private LocalDateTime createdOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", account=" + account.getAccountId() +
                ", paymentType=" + paymentType +
                ", creditCard='" + creditCard + '\'' +
                ", amount=" + amount +
                ", createdOn=" + createdOn +
                '}';
    }
}