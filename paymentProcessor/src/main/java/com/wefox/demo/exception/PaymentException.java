package com.wefox.demo.exception;

import com.wefox.domain.dto.Error;

public class PaymentException extends Exception {

    private Error error;

    public PaymentException(Error error) {
        super(error.getErrorDescription());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
