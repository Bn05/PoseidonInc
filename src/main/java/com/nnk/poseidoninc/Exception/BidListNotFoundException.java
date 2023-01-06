package com.nnk.poseidoninc.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BidListNotFoundException extends RuntimeException {

    public BidListNotFoundException() {
        super();
    }
}
