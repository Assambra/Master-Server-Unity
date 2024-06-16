package com.assambra.app.service;

import com.assambra.app.model.PlayerDespawnModel;
import com.assambra.app.model.PlayerSpawnModel;
import com.assambra.common.entity.Character;
import com.assambra.common.entity.CharacterLocation;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.gamebox.manager.PlayerManager;
import com.tvd12.gamebox.math.Vec3;
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
     *        Character "character.getName()" or "UnityPlayer player.getName()"
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
     *        "EzyUser ezyuser.getName()" or "User user.getUsername" or Character character.getUsername()
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

    public PlayerSpawnModel getPlayerSpawnModel (Character character, CharacterLocation characterLocation)
    {
        double[] pos = characterLocation.getPosition();
        double[] rot = characterLocation.getRotation();

        return PlayerSpawnModel.builder()
                .id(character.getId())
                .name(character.getName())
                .username(character.getUsername())
                .position(
                        new Vec3(
                                (float)pos[0],
                                (float)pos[1],
                                (float)pos[2]
                        ).toArray()
                )
                .rotation(
                        new Vec3(
                                (float)rot[0],
                                (float)rot[1],
                                (float)rot[2]
                        ).toArray()
                )
                .build();
    }

    public PlayerDespawnModel getPlayerDespawnModel(Long id)
    {
        return PlayerDespawnModel.builder()
                .id(id)
                .build();
    }
}
