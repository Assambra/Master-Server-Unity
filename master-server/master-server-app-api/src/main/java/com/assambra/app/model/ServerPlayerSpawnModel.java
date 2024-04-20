package com.assambra.app.model;

import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServerPlayerSpawnModel {
    private String username;
    private EzyArray position;
    private EzyArray rotation;
}
