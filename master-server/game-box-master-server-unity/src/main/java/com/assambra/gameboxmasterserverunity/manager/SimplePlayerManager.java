package com.assambra.gameboxmasterserverunity.manager;

import com.assambra.gameboxmasterserverunity.entity.Player;

public class SimplePlayerManager<P extends Player>
    extends AbstractPlayerManager<P> {

    public SimplePlayerManager() {
        this(999999999);
    }

    public SimplePlayerManager(int maxPlayer) {
        super(maxPlayer);
    }

    protected SimplePlayerManager(Builder<?, ?> builder) {
        super(builder);
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<U extends Player, B extends Builder<U, B>>
        extends AbstractPlayerManager.Builder<U, B> {

        @Override
        protected PlayerManager<U> newProduct() {
            return new SimplePlayerManager<>(this);
        }
    }
}
