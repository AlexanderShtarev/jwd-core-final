package com.epam.jwd.core_final.exception;

public class InvalidStateException extends Exception {
    // todo
    public InvalidStateException (String msg) {
        super(msg);
        System.out.println(msg);
        //Logger.LOGGER.error(msg);
    }
}