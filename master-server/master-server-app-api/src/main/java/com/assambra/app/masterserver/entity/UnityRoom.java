package com.assambra.app.masterserver.entity;

import com.assambra.app.masterserver.model.ServerModel;
import com.assambra.app.masterserver.util.RandomStringUtil;
import com.assambra.gameboxmasterserverunity.entity.NormalRoom;
import com.assambra.app.masterserver.server.UnityServer;

import java.io.IOException;

public class UnityRoom extends NormalRoom {
    protected final UnityServer unityServer;
    protected final String randomPassword;
    protected Process unityProcess;
    private ServerModel serverDetails;


    public UnityRoom(Builder builder) {
        super(builder);

        this.randomPassword = RandomStringUtil.getAlphaNumericString(6);

        this.unityServer = new UnityServer.Builder()
                .username(this.name)
                .password(randomPassword)
                .room(this.name)
                .build();

        try {
            this.unityProcess = unityServer.start();
            if (this.unityProcess != null) {

                this.serverDetails = ServerModel.builder()
                        .username(this.name)
                        .password(randomPassword)
                        .room(this.name)
                        .process(unityProcess)
                        .build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Todo create room exception
        }
    }

    public ServerModel getServerDetails() {
        return this.serverDetails;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends NormalRoom.Builder<Builder> {
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
