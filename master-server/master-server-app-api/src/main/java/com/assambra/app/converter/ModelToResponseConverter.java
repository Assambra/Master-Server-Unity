package com.assambra.app.converter;

import com.assambra.app.model.CharacterInfoModel;
import com.assambra.app.model.CharacterModel;
import com.assambra.app.response.CharacterInfoResponse;
import com.assambra.app.response.CharacterResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ModelToResponseConverter {

    public CharacterInfoResponse toResponse(CharacterInfoModel model){
        return CharacterInfoResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
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
