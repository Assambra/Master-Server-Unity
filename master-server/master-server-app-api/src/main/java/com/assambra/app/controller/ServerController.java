package com.assambra.app.controller;

import com.assambra.common.masterserver.constant.Commands;
import com.assambra.app.service.ServerService;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class ServerController extends EzyLoggable {

    private final ServerService serverService;
    private final EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.SERVER_READY)
    public void serverReady(EzyUser user)
    {
        logger.info("Receive Commands.SERVER_READY from Server: {}", user.getName());
        serverService.setServerReady(user);
    }
}
