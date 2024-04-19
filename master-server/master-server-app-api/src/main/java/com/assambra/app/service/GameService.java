package com.assambra.app.service;

import com.assambra.common.masterserver.entity.UnityPlayer;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.manager.PlayerManager;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@EzySingleton("gameService")
public class GameService extends EzyLoggable {

    private final PlayerManager globalPlayerManager;

    public void addPlayerToGlobalPlayerList(UnityPlayer player)
    {
        globalPlayerManager.addPlayer(player);
    }

    public void removePlayerFromGlobalPlayerList(UnityPlayer player)
    {
        globalPlayerManager.removePlayer(player);
    }
}
