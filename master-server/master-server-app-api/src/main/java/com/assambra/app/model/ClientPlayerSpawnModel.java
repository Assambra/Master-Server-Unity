package com.assambra.app.model;

import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientPlayerSpawnModel {
    private Long id;
    private String name;
    private boolean isLocalPlayer;
    private String roomName;
    private EzyArray position;
    private EzyArray rotation;
}
