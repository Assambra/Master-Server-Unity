package com.assambra.app.converter;

import com.assambra.app.model.CharacterModel;
import com.assambra.app.response.CharacterResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ModelToResponseConverter {

    public CharacterResponse toResponse (CharacterModel model){
        return CharacterResponse.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .name(model.getName())
                .build();
    }
}
