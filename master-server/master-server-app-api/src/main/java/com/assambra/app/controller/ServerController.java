package com.assambra.app.controller;

import com.assambra.common.masterserver.constant.Commands;
import com.assambra.app.service.ServerService;
import com.assambra.common.masterserver.request.ClientToServerRequest;
import com.assambra.common.masterserver.request.ServerToClientRequest;
import com.assambra.common.masterserver.request.ServerToClientsRequest;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.command.EzyObjectResponse;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@EzyRequestController
public class ServerController extends EzyLoggable {

    private final ServerService serverService;
    private final EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.SERVER_READY)
    public void serverReady(EzyUser ezyUser)
    {
        logger.info("Receive Commands.SERVER_READY from Server: {}", ezyUser.getName());
        serverService.setServerReady(ezyUser);
    }

    @EzyDoHandle(Commands.CLIENT_TO_SERVER)
    public void clientToServer(EzyUser ezyUser, ClientToServerRequest request)
    {
        logger.info("Receive Commands.CLIENT_TO_SERVER from Client: {}", ezyUser.getName());

        EzyObjectResponse response = responseFactory.newObjectResponse()
                .command(request.getCommand())
                .username(request.getRoom());

        Map<String, Object> additionalParams = request.getAdditionalParams();
        for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
            response.param(entry.getKey(), entry.getValue());
        }

        response.execute();
    }

    @EzyDoHandle(Commands.SERVER_TO_CLIENT)
    public void serverToClient(EzyUser ezyUser, ServerToClientRequest request)
    {
        logger.info("Receive Commands.SERVER_TO_CLIENT from Server: {}", ezyUser.getName());
        logger.info("Receive .command {}", request.getCommand());
        logger.info("Receive .username {}", request.getRecipient());


        EzyObjectResponse response = responseFactory.newObjectResponse()
                .command(request.getCommand())
                .username(request.getRecipient());

        Map<String, Object> additionalParams = request.getAdditionalParams();
        for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
            logger.info("Receive .param({},{})", entry.getKey(), entry.getValue());
            response.param(entry.getKey(), entry.getValue());
        }

        response.execute();
    }

    @EzyDoHandle(Commands.SERVER_TO_CLIENTS)
    public void serverToClients(EzyUser ezyUser, ServerToClientsRequest request)
    {
        logger.info("Receive Commands.SERVER_TO_CLIENTS from Server: {}", ezyUser.getName());

        EzyObjectResponse response = responseFactory.newObjectResponse()
                .command(request.getCommand())
                .usernames(request.getRecipients().toList());

        Map<String, Object> additionalParams = request.getAdditionalParams();
        for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
            response.param(entry.getKey(), entry.getValue());
        }

        response.execute();
    }
}
