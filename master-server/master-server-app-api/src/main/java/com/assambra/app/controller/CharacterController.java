package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.assambra.app.converter.ModelToResponseConverter;
import com.assambra.app.model.CharacterListModel;
import com.assambra.app.request.CreateCharacterRequest;
import com.assambra.app.service.CharacterService;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@AllArgsConstructor
@EzyRequestController
public class CharacterController extends EzyLoggable {

    private final EzyResponseFactory responseFactory;
    private final CharacterService characterService;
    private final ModelToResponseConverter modelToResponseConverter;

    @EzyDoHandle(Commands.CHARACTER_LIST)
    public void characterList(EzyUser ezyuser)
    {
        logger.info("Receive: Commands.CHARACTER_LIST from user: {}", ezyuser.getName());

        CharacterListModel characterListModel = characterService.getCharacterListModel(ezyuser);

        responseFactory.newArrayResponse()
                .command(Commands.CHARACTER_LIST)
                .data(
                        newArrayList(
                                characterListModel.getCharacters(),
                                modelToResponseConverter::toResponse
                        )
                )
                .user(ezyuser)
                .execute();
    }

    @EzyDoHandle(Commands.CREATE_CHARACTER)
    public void createCharacter(EzyUser ezyuser, CreateCharacterRequest request)
    {
        logger.info("Receive: Commands.CREATE_CHARACTER from user: {}", ezyuser.getName());

        if(!characterService.characterExist(request.getName()))
        {
            logger.info("User: {}, successfully create new character: {}", ezyuser.getName(), request.getName());
            characterService.createCharacter(ezyuser, request);
        }
        else
        {
            logger.info("User: {}, tried to create new character: {}, but it already exists.", ezyuser.getName(), request.getName());
        }
    }

    @EzyDoHandle(Commands.PLAY)
    public void play(EzyUser ezyuser)
    {
        logger.info("Receive: Commands.PLAY from user: {}", ezyuser.getName());
    }
}
