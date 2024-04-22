package com.assambra.app.converter;

import com.assambra.app.model.*;
import com.assambra.app.response.CharacterInfoResponse;
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

    public EzyObjectResponse toResponse(PlayerSpawnModel model)
    {
        return responseFactory.newObjectResponse()
                .param("name", model.getName())
                .param("username", model.getUsername())
                .param("position", model.getPosition())
                .param("rotation", model.getRotation());
    }
}
