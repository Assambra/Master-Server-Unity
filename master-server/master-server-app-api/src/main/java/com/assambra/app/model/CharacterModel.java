package com.assambra.app.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterModel {
    private Long id;
    private Long userId;
    private String name;
}
