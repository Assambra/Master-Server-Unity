package com.assambra.gameboxmasterserverunity.manager;

import com.assambra.gameboxmasterserverunity.entity.Room;

public class DefaultRoomManager<R extends Room> extends AbstractRoomManager<R> {

    public DefaultRoomManager() {
        this(10000);
    }

    public DefaultRoomManager(int maxRoom) {
        super(maxRoom);
    }

    protected DefaultRoomManager(Builder<?, ?> builder) {
        super(builder);
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<R extends Room, B extends Builder<R, B>>
        extends AbstractRoomManager.Builder<R, B> {

        @Override
        public DefaultRoomManager<R> build() {
            return new DefaultRoomManager<>(this);
        }
    }
}
