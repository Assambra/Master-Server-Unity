package com.assambra.app.converter;

import com.assambra.app.model.*;
import com.assambra.app.response.CharacterInfoResponse;
import com.assambra.app.response.CharacterResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.support.command.EzyObjectResponse;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;


@EzySingleton
@AllArgsConstructor
public class ModelToResponseConverter {

    private final EzyResponseFactory responseFactory;

    public CharacterInfoResponse toResponse(CharacterInfoModel model){
        return CharacterInfoResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .room(model.getRoom())
                .build();
    }

    public EzyObjectResponse toResponse(ServerPlayerSpawnModel model)
    {
        return responseFactory.newObjectResponse()
                .param("username", model.getUsername())
                .param("position", model.getPosition())
                .param("rotation", model.getRotation());
    }

    public EzyObjectResponse toResponse(ClientPlayerSpawnModel model)
    {
        return responseFactory.newObjectResponse()
                .param("id", model.getId())
                .param("name", model.getName())
                .param("isLocalPlayer", model.isLocalPlayer())
                .param("roomName", model.getRoomName())
                .param("position", model.getPosition())
                .param("rotation", model.getRotation());
    }

    public CharacterResponse toResponse (CharacterModel model){
        return CharacterResponse.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .name(model.getName())
                .username(model.getUsername())
                .build();
    }
}
