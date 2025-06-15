package de.dhbw.exceptions;

public class UnexpectedResultException extends Exception {
    public UnexpectedResultException(Object o) {
        super("Unexpected Result was returned in " + o.getClass().getSimpleName());
    }
}
