package com.assambra.app.controller;

import com.assambra.app.constant.Commands;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class CharacterController extends EzyLoggable {

    private final EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.CHARACTER_LIST)
    public void characterList(EzyUser user)
    {
        logger.info("Receive: Commands.CHARACTER_LIST from user: {}", user.getName());
    }
    @EzyDoHandle(Commands.CREATE_CHARACTER)
    public void createCharacter(EzyUser user)
    {
        logger.info("Receive: Commands.CREATE_CHARACTER from user: {}", user.getName());
    }
    @EzyDoHandle(Commands.PLAY)
    public void play(EzyUser user)
    {
        logger.info("Receive: Commands.PLAY from user: {}", user.getName());
    }
}
