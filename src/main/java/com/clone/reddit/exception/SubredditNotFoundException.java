package com.clone.reddit.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class SubredditNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SubredditNotFoundException(String message) {
        super(message);
    }
}