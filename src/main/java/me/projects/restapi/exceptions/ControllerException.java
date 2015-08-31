package me.projects.restapi.exceptions;

public class ControllerException extends Exception {
    static final long serialVersionUID = 1L;

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable causeEx) {
        super(message, causeEx);
    }

}
