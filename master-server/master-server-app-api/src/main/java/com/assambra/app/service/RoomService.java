package com.assambra.app.service;

import com.assambra.app.constant.Commands;
import com.assambra.app.converter.ModelToResponseConverter;
import com.assambra.app.model.PlayerSpawnModel;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.manager.RoomManager;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@EzySingleton("roomService")
public class RoomService extends EzyLoggable {

    private final RoomManager globalRoomManager;
    private final ModelToResponseConverter modelToResponseConverter;

    public UnityRoom getRoom(String roomName)
    {
        return (UnityRoom)globalRoomManager.getRoom(roomName);
    }

    public UnityRoom getRoom(Long id)
    {
        return (UnityRoom)globalRoomManager.getRoom(id);
    }

    public void addPlayerToRoom(UnityPlayer player, String roomName, PlayerSpawnModel playerSpawnModel)
    {
        UnityRoom room = getRoom(roomName);
        room.addPlayer(player);

        modelToResponseConverter.toResponse(playerSpawnModel)
                .command(Commands.PLAYER_SPAWN)
                .username(roomName)
                .execute();
    }

    public void removePlayerFromRoom(UnityPlayer player)
    {
        UnityRoom room = getRoom(player.getCurrentRoomId());
        room.removePlayer(player);
        player.setCurrentRoomId(0);

        /* Todo
        Send command. SERVER_PLAYER_DESPAWN to room server;
         */
    }
}
