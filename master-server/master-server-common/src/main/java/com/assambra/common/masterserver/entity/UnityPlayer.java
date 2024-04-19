package com.assambra.common.masterserver.entity;

import com.tvd12.gamebox.entity.Player;
import lombok.Getter;
import lombok.Setter;

public class UnityPlayer extends Player {
    public UnityPlayer(String name) {
        super(name);
    }
    @Getter @Setter
    protected String username;

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
