package com.assambra.app.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterInfoModel {
    private Long id;
    private String name;
    private String room;
}
