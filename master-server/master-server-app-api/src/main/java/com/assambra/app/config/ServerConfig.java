package com.assambra.app.config;

import com.assambra.app.masterserver.entity.UnityPlayer;
import com.assambra.app.masterserver.manager.SynchronizedUnityPlayerManager;
import com.assambra.app.masterserver.manager.SynchronizedUnityRoomManager;
import com.assambra.app.masterserver.entity.UnityRoom;
import com.assambra.gameboxmasterserverunity.manager.PlayerManager;
import com.assambra.gameboxmasterserverunity.manager.RoomManager;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;

@EzyConfigurationBefore(priority = 0)
public class ServerConfig extends EzyLoggable {

    @EzySingleton("globalRoomManager")
    public RoomManager<UnityRoom> globalRoomManager() {

        RoomManager<UnityRoom> roomManager = new SynchronizedUnityRoomManager<>();

        roomManager.addRoom(world());

        return roomManager;
    }

    @EzySingleton("globalPlayerManager")
    public PlayerManager<UnityPlayer> globalPlayerManager() {
        return new SynchronizedUnityPlayerManager<>();
    }

    private UnityRoom world() {
        logger.info("Initialize world room");

        return UnityRoom.builder()
                .id(0)
                .maxPlayer(10000)
                .name("World")
                .build();
    }
}
