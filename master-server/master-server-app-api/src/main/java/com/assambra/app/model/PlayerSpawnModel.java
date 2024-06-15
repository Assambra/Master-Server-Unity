package com.assambra.app.model;

import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerSpawnModel {
    private Long id;
    private String name;
    private String username;
    private EzyArray position;
    private EzyArray rotation;
}
