package com.nnk.poseidoninc.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException() {
        super();
    }
}

