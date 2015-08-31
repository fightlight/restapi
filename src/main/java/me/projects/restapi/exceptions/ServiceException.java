package me.projects.restapi.exceptions;

public class ServiceException extends Exception {
    private final static long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
