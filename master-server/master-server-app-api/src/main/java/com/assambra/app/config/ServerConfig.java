package com.assambra.app.config;

import com.assambra.gameboxmasterserverunity.entity.NormalRoom;
import com.assambra.gameboxmasterserverunity.entity.Player;
import com.assambra.app.masterserver.entity.UnityRoom;
import com.assambra.gameboxmasterserverunity.manager.PlayerManager;
import com.assambra.gameboxmasterserverunity.manager.RoomManager;
import com.assambra.gameboxmasterserverunity.manager.SynchronizedPlayerManager;
import com.assambra.gameboxmasterserverunity.manager.SynchronizedRoomManager;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;

@EzyConfigurationBefore(priority = 0)
public class ServerConfig extends EzyLoggable {

    @EzySingleton("globalRoomManager")
    public RoomManager<NormalRoom> globalRoomManager() {

        RoomManager<NormalRoom> roomManager = new SynchronizedRoomManager<>();

        roomManager.addRoom(world());

        return roomManager;
    }

    @EzySingleton("globalPlayerManager")
    public PlayerManager<Player> globalPlayerManager() {
        return new SynchronizedPlayerManager<>();
    }

    private UnityRoom world() {
        logger.info("Initialize world room");

        return UnityRoom.builder()
                .id(1)
                .name("World")
                .build();
    }
}
