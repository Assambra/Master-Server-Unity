package com.assambra.app.masterserver.entity;

import com.assambra.gameboxmasterserverunity.entity.Player;

public class UnityPlayer extends Player {
    public UnityPlayer(String name) {
        super(name);
    }

    protected UnityPlayer(UnityPlayer.Builder builder) {
        super(builder);
    }

    public static UnityPlayer.Builder builder() {
        return new UnityPlayer.Builder();
    }

    public static class Builder extends Player.Builder<UnityPlayer.Builder> {

        @Override
        protected Player newProduct() {
            return new UnityPlayer(this);
        }

        @Override
        public UnityPlayer build() {
            return (UnityPlayer) super.build();
        }
    }
}
