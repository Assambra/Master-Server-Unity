package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.assambra.app.model.PlayerSpawnModel;
import com.assambra.app.request.PlayRequest;
import com.assambra.app.service.CharacterService;
import com.assambra.app.service.PlayerService;
import com.assambra.app.service.RoomService;
import com.assambra.common.entity.Character;
import com.assambra.common.entity.CharacterLocation;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class GameController extends EzyLoggable {

    private final CharacterService characterService;
    private final PlayerService playerService;
    private final RoomService roomService;

    @EzyDoHandle(Commands.PLAY)
    public void play(EzyUser ezyuser, PlayRequest request)
    {
        logger.info("Receive: Commands.PLAY from user: {}", ezyuser.getName());

        Character character = characterService.getCharacter(request.getId());
        CharacterLocation characterLocation = characterService.getCharacterLocation(character.getId());

        UnityPlayer player = new UnityPlayer(character.getName());
        player.setUsername(ezyuser.getName());
        player.setId(character.getId());
        playerService.addPlayerToGlobalPlayerList(player);

        PlayerSpawnModel serverPlayerSpawnModel = playerService.getPlayerSpawnModel(character, characterLocation);

        roomService.addPlayerToRoom(player, characterLocation.getRoom(), serverPlayerSpawnModel);
    }
}
