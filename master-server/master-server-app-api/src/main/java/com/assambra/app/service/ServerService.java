package com.assambra.app.service;

import com.assambra.common.masterserver.constant.UnityRoomStatus;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.gamebox.manager.RoomManager;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@EzySingleton("serverServiceApp")
public class ServerService extends EzyLoggable {

    private final RoomManager<UnityRoom> globalRoomManager;

    public void setServerReady(EzyUser user)
    {
        for(UnityRoom room : globalRoomManager.getRoomList())
        {
            if(room.getName().equals(user.getName()))
            {
                logger.info("Set room: {} to ready", room.getName());
                room.setReady(true);
            }
        }
    }

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
