package com.deancatterson.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NullTeamIdException extends Throwable {

    public NullTeamIdException(String errorMessage) {

    }
}
