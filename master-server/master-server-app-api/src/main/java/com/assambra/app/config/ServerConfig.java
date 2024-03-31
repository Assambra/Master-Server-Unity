package com.assambra.app.config;

import com.assambra.gameboxmasterserverunity.entity.NormalRoom;
import com.assambra.gameboxmasterserverunity.entity.Player;
import com.assambra.gameboxmasterserverunity.manager.PlayerManager;
import com.assambra.gameboxmasterserverunity.manager.RoomManager;
import com.assambra.gameboxmasterserverunity.manager.SynchronizedPlayerManager;
import com.assambra.gameboxmasterserverunity.manager.SynchronizedRoomManager;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import lombok.Setter;

@Setter
@EzyConfigurationBefore(priority = 0)
public class ServerConfig extends EzyLoggable {

    @EzySingleton("globalRoomManager")
    public RoomManager<NormalRoom> globalRoomManager() {

        RoomManager<NormalRoom> roomManager = new SynchronizedRoomManager<>();

        NormalRoom[] staticRooms = new NormalRoom[2];
        staticRooms[0] = lobby();
        staticRooms[1] = world();

        roomManager.addRooms(staticRooms);

        /*
        for (NormalRoom room : roomManager.getRoomList())
        {
            logger.info("Room: {}", room.getName());
        }
        */
        return roomManager;
    }

    @EzySingleton("globalPlayerManager")
    public PlayerManager<Player> globalPlayerManager() {
        return new SynchronizedPlayerManager<>();
    }

    private NormalRoom lobby() {
        logger.info("Initialize lobby room");
        return (NormalRoom) NormalRoom.builder()
                .id(0)
                .name("Lobby")
                .build();
    }

    private NormalRoom world() {
        logger.info("Initialize world room");
        return (NormalRoom)NormalRoom.builder()
                .id(1)
                .name("World")
                .build();
    }
}
