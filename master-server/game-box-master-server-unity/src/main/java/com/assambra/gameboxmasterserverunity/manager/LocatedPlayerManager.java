package com.assambra.gameboxmasterserverunity.manager;

import com.tvd12.ezyfox.function.EzyPredicates;
import com.assambra.gameboxmasterserverunity.entity.LocatedPlayer;

import java.util.List;
import java.util.function.Predicate;

public interface LocatedPlayerManager {

    LocatedPlayer setNewMaster();

    LocatedPlayer getMaster();

    void setMaster(LocatedPlayer master);

    LocatedPlayer getSpeakinger();

    void setSpeakinger(LocatedPlayer speakinger);

    LocatedPlayer getPlayer(int location);

    void addPlayer(LocatedPlayer player, int location);

    LocatedPlayer removePlayer(int location);

    LocatedPlayer nextOf(LocatedPlayer player, Predicate<LocatedPlayer> condition);

    default LocatedPlayer nextOf(LocatedPlayer player) {
        return nextOf(player, EzyPredicates.alwaysTrue());
    }

    LocatedPlayer rightOf(LocatedPlayer player, Predicate<LocatedPlayer> condition);

    default LocatedPlayer rightOf(LocatedPlayer player) {
        return rightOf(player, EzyPredicates.alwaysTrue());
    }

    LocatedPlayer leftOf(LocatedPlayer player, Predicate<LocatedPlayer> condition);

    default LocatedPlayer leftOf(LocatedPlayer player) {
        return leftOf(player, EzyPredicates.alwaysTrue());
    }

    List<String> getPlayerNames();

    boolean containsPlayer(String username);

    int getPlayerCount();

    boolean isEmpty();
}
