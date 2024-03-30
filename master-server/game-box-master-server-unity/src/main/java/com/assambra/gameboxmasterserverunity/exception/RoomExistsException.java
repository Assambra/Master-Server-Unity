package com.assambra.gameboxmasterserverunity.exception;

public class RoomExistsException extends IllegalArgumentException {
    private static final long serialVersionUID = -3890769973167703303L;

    public RoomExistsException(String roomName) {
        super("room: " + roomName + " has added");
    }
}
