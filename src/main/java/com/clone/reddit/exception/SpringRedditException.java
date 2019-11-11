package com.clone.reddit.exception;

public class SpringRedditException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SpringRedditException(String message) {
        super(message);
    }
}