package com.assambra.gameboxmasterserverunity.exception;

public class LocationNotAvailableException extends IllegalArgumentException {
    private static final long serialVersionUID = 7903554823337949693L;

    public LocationNotAvailableException(String msg) {
        super(msg);
    }
}
