package com.assambra.app.service;

import com.assambra.common.constant.UnityRoomStatus;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.gamebox.manager.RoomManager;

import java.util.List;

@EzySingleton
public class ServerService extends EzyLoggable {

    @EzyAutoBind
    private RoomManager<UnityRoom> globalRoomManager;

    public void setServerStatus(EzyUser user, UnityRoomStatus status)
    {
        for(UnityRoom room : globalRoomManager.getRoomList())
        {
            if(room.getName().equals(user.getName()))
            {
                logger.info("Set status of room: {} to UnityRoomStatus.{}", room.getName(), status.getName());
                room.setStatus(status);
            }
        }
    }

    public List<UnityRoom> getServers()
    {
        return globalRoomManager.getRoomList();
    }
}
