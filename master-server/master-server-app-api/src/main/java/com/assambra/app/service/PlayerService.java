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

    /**
     * This method found a UnityPlayer in the globalPlayerList by its "Character character.getName()" name
     * or "UnityPlayer player.getName())" name. It takes a {@code String} parameter name;
     *
     * @param name
     *        Character "character.getName()" or "UnityPlayer player.getName() but we search here for a UnityPlayer object so we don't know about the player.getName()"
     *
     * @return A UnityPlayer object if found else returns {@code NULL}
     */

    public UnityPlayer getPlayerByNameFromGlobalPlayerManager(String name)
    {
        return (UnityPlayer) globalPlayerManager.getPlayer(name);
    }

    /**
     * This method found a UnityPlayer in the globalPlayerList by its "EzyUser ezyuser.getName()" username
     * or "User user.getUsername" username. It takes a username as {@code String}
     *
     * @param username
     *        "EzyUser ezyuser.getName()" or "User user.getUsername"
     *
     * @return A UnityPlayer object if found else returns {@code NULL}
     */

    public UnityPlayer getPlayerByUsernameFromGlobalPlayerManager(String username) {
        List<UnityPlayer> players = globalPlayerManager.getPlayerList();
        return players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
