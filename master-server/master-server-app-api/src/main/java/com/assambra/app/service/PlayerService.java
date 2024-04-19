package com.assambra.app.service;

import com.assambra.common.masterserver.entity.UnityPlayer;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.manager.PlayerManager;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@EzySingleton("playerService")
public class PlayerService extends EzyLoggable {

    private final PlayerManager globalPlayerManager;

    public void addPlayerToGlobalPlayerList(UnityPlayer player)
    {
        globalPlayerManager.addPlayer(player);
    }

    public void removePlayerFromGlobalPlayerList(UnityPlayer player)
    {
        globalPlayerManager.removePlayer(player);
    }

    public UnityPlayer getPlayerByName(String name)
    {
        return (UnityPlayer) globalPlayerManager.getPlayer(name);
    }

    public UnityPlayer getPlayerByUsername(String username) {
        List<UnityPlayer> players = globalPlayerManager.getPlayerList();
        return players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
