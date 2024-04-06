package com.assambra.app.config;

import com.assambra.common.masterserver.manager.SynchronizedUnityPlayerManager;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.assambra.gameboxmasterserverunity.manager.PlayerManager;
import com.assambra.gameboxmasterserverunity.manager.RoomManager;
import com.assambra.gameboxmasterserverunity.manager.SynchronizedRoomManager;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;

@EzyConfigurationBefore(priority = 1)
public class GlobalManagerConfig extends EzyLoggable {

    @EzySingleton("globalRoomManager")
    public RoomManager<UnityRoom> globalRoomManager() {
        return new SynchronizedRoomManager<>();
    }

    @EzySingleton("globalPlayerManager")
    public PlayerManager<UnityPlayer> globalPlayerManager() {
        return new SynchronizedUnityPlayerManager<>();
    }
}
