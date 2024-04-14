package com.assambra.app.model;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CharacterListModel {
    private List<CharacterModel> characters;
}
