package com.wefox.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    public enum ErrorType {
        database,
        network,
        other;
    }

    @JsonProperty("payment_id")
    private String paymentId;

    private ErrorType error;

    @JsonProperty("error_description")
    private String errorDescription;

    @Override
    public String toString() {
        return "Error{" +
                "paymentId='" + paymentId + '\'' +
                ", error=" + error +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}