package com.assambra.gameboxmasterserverunity.exception;

public class NoSlotException extends RuntimeException {
    private static final long serialVersionUID = 3679314239894207382L;

    public NoSlotException(String msg) {
        super(msg);
    }
}
