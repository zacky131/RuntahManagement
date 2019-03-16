package com.resikin.percakapan.core.users.models.exception;

/**
 * Created by andrealeo on 24/01/18.
 */

public class ChatUserIdException extends RuntimeException {

    public ChatUserIdException() {
        super();
    }

    public ChatUserIdException(String message) {
        super(message);
    }

    public ChatUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatUserIdException(Throwable cause) {
        super(cause);
    }

}
