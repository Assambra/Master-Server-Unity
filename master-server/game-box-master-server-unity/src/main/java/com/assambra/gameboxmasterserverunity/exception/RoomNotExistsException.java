package com.assambra.gameboxmasterserverunity.exception;

public class RoomNotExistsException extends IllegalArgumentException {
    private static final long serialVersionUID = -3890769973167703303L;

    public RoomNotExistsException(String message) {
        super(message);
    }
}
