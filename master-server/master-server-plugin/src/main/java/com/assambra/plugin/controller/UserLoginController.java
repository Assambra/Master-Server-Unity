package com.assambra.plugin.controller;

import com.assambra.common.entity.User;
import com.assambra.common.masterserver.entity.UnityRoom;
import com.assambra.plugin.service.ServerService;
import com.assambra.plugin.service.UserService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.security.EzySHA256;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import lombok.AllArgsConstructor;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

@EzySingleton
@EzyEventHandler(USER_LOGIN)
@AllArgsConstructor
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

    private final UserService userService;
    private final ServerService serverServicePlugin;

    @Override
    public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {

        String username = event.getUsername();
        String password = encodePassword(event.getPassword());
        User user = userService.getUser(username);

        if(!serverServicePlugin.getServerUsernames().contains(username))
        {
            if (user == null) {
                logger.info("User doesn't exist in db, create a new one!");
                user = userService.createUser(username, password);
                userService.saveUser(user);
            }

            if (!user.getPassword().equals(password)) {
                throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
            }

            logger.info("user and password match, accept user: {}", username);
        }
        else
        {
            for(UnityRoom server : serverServicePlugin.getServers())
            {
                if(server.getName().equals(username))
                {
                    if(server.getUserPassword().equals(event.getPassword()))
                        logger.info("Server: {}, logged in", username);
                    else
                    {
                        logger.info("Server: {}, use wrong password", username);
                        throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
                    }
                }
            }
        }
    }

    private String encodePassword(String password) {
        return EzySHA256.cryptUtfToLowercase(password);
    }
}
