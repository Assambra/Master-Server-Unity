package com.assambra.app.controller;

import com.assambra.app.service.PlayerService;
import com.assambra.common.masterserver.entity.UnityPlayer;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserRemovedEvent;
import com.tvd12.gamebox.manager.PlayerManager;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_REMOVED;

@EzySingleton
@AllArgsConstructor
@EzyEventHandler(USER_REMOVED)
public class UserRemovedController extends EzyAbstractAppEventController<EzyUserRemovedEvent> {

    private final PlayerManager globalPlayerManager;
    private final PlayerService playerService;

    @Override
    public void handle(EzyAppContext ctx, EzyUserRemovedEvent event) {

        List<UnityPlayer> players = globalPlayerManager.getPlayerList();

        for(UnityPlayer player : players)
        {
            if(player.getUsername().equals(event.getUser().getName()));
                playerService.removePlayerFromGlobalPlayerList(player);
        }
    }
}
