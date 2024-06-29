package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.assambra.app.model.PlayerDespawnModel;
import com.assambra.app.model.PlayerSpawnModel;
import com.assambra.app.request.ChangeServerRequest;
import com.assambra.app.service.PlayerService;
import com.assambra.app.service.RoomService;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.gamebox.math.Vec3;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class RoomController extends EzyLoggable {

    private final RoomService roomService;
    private final PlayerService playerService;

    @EzyDoHandle(Commands.CHANGE_SERVER)
    public void changeServer(EzyUser ezyUser, ChangeServerRequest request)
    {
        UnityPlayer player = playerService.getPlayerByIdFromGlobalPlayerManager(request.getId());

        Vec3 position = new Vec3(
                request.getPosition().get(0, float.class),
                request.getPosition().get(1, float.class),
                request.getPosition().get(2, float.class)
        );

        Vec3 rotation = new Vec3(
                request.getPosition().get(0, float.class),
                request.getPosition().get(1, float.class),
                request.getPosition().get(2, float.class)
        );

        PlayerDespawnModel playerDespawnModel = playerService.getPlayerDespawnModel(request.getId());
        roomService.removePlayerFromRoom(player, playerDespawnModel);

        PlayerSpawnModel playerSpawnModel = playerService.getPlayerSpawnModel(player, position, rotation);
        roomService.addPlayerToRoom(player, request.getRoom(), playerSpawnModel);
    }
}
