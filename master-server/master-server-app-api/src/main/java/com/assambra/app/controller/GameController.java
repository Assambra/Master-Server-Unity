package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.assambra.app.converter.ModelToResponseConverter;
import com.assambra.app.model.ServerPlayerSpawnModel;
import com.assambra.app.request.PlayRequest;
import com.assambra.app.service.CharacterService;
import com.assambra.app.service.PlayerService;
import com.assambra.app.service.RoomService;
import com.assambra.common.entity.Character;
import com.assambra.common.entity.CharacterLocation;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class GameController extends EzyLoggable {

    private final EzyResponseFactory responseFactory;
    private final CharacterService characterService;
    private final PlayerService playerService;
    private final ModelToResponseConverter modelToResponseConverter;
    private final RoomService roomService;

    @EzyDoHandle(Commands.PLAY)
    public void play(EzyUser ezyuser, PlayRequest request)
    {
        logger.info("Receive: Commands.PLAY from user: {}", ezyuser.getName());

        Character character = characterService.getCharacter(request.getId());
        CharacterLocation characterLocation = characterService.getCharacterLocation(character.getId());

        UnityPlayer player = new UnityPlayer(character.getName());
        player.setUsername(ezyuser.getName());
        playerService.addPlayerToGlobalPlayerList(player);

        ServerPlayerSpawnModel serverSpawnModel =  playerService.getServerSpawnModel(character, characterLocation);

        roomService.addPlayerToRoom(player, characterLocation.getRoomName(), serverSpawnModel);

        /* Todo
        //Send to Player -> Spawn
        responseFactory.newObjectResponse(
                .command(Commands.SPAWN)
                //params or array, roomName, position, rotation, (maybe isLocalPlayer = true, not sure at the moment)
                .user(ezyuser)
                .execute();
        )
        */
    }
}
