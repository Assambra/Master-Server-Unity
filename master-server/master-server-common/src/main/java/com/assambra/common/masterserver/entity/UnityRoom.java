package com.assambra.common.masterserver.entity;

import com.assambra.common.constant.UnityRoomStatus;
import com.assambra.common.masterserver.manager.SynchronizedUnityPlayerManager;
import com.assambra.common.masterserver.server.UnityServer;
import com.assambra.common.masterserver.util.RandomStringUtil;
import com.tvd12.gamebox.entity.NormalRoom;
import com.tvd12.gamebox.entity.Player;
import com.tvd12.gamebox.manager.PlayerManager;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class UnityRoom extends NormalRoom {
    protected final UnityServer unityServer;
    @Getter
    protected final String userPassword;
    @Getter
    protected Process unityProcess;
    @Getter
    @Setter
    protected UnityPlayer master;

    public UnityRoom(Builder builder) {
        super(builder);

        this.status = UnityRoomStatus.NONE;
        this.userPassword = RandomStringUtil.getAlphaNumericString(6);

        this.unityServer = new UnityServer.Builder()
                .username(this.name)
                .password(userPassword)
                .room(this.name)
                .build();

        try {
            this.unityProcess = unityServer.start();
        } catch (IOException e) {
            e.printStackTrace();
            // Todo create room exception
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addPlayer(Player player) {
        if (!(player instanceof UnityPlayer)) {
            throw new IllegalArgumentException("Player " + player.getName() + " must be UnityPlayer");
        }

        if (playerManager.containsPlayer(player)) {
            return;
        }

        synchronized (this) {
            if (playerManager.isEmpty()) {
                master = (UnityPlayer) player;
            }
            super.addPlayer(player);
        }
    }

    public static class Builder extends NormalRoom.Builder<Builder> {

        protected int maxPlayer = 999;

        public UnityRoom.Builder maxPlayer(int maxPlayer) {
            this.maxPlayer = maxPlayer;
            return this;
        }

        @Override
        public UnityRoom.Builder defaultPlayerManager(int maxPlayer) {
            this.playerManager = new SynchronizedUnityPlayerManager<>(maxPlayer);
            return this;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public UnityRoom.Builder playerManager(PlayerManager playerManager) {
            if (playerManager instanceof SynchronizedUnityPlayerManager) {
                return super.playerManager(playerManager);
            }
            throw new IllegalArgumentException("playerManager must be SynchronizedUnityPlayerManager");
        }

        @Override
        protected void preBuild() {
            if (playerManager == null) {
                playerManager = new SynchronizedUnityPlayerManager<>(maxPlayer);
            }

            super.preBuild();
        }

        @Override
        public UnityRoom build() {
            return new UnityRoom(this);
        }

        @Override
        protected UnityRoom newProduct() {
            return new UnityRoom(this);
        }
    }
}
