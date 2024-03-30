package com.assambra.gameboxmasterserverunity.handler;

import com.assambra.gameboxmasterserverunity.entity.MMORoom;
import com.assambra.gameboxmasterserverunity.entity.MMOPlayer;

import java.util.List;

public interface MMORoomUpdatedHandler {

    void onRoomUpdated(MMORoom room, List<MMOPlayer> players);
}
