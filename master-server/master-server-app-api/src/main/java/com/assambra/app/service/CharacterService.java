package com.assambra.app.service;

import com.assambra.common.entity.Character;
import com.assambra.common.entity.User;
import com.assambra.common.repo.CharacterRepo;
import com.assambra.common.repo.UserRepo;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@EzySingleton("characterService")
public class CharacterService extends EzyLoggable {

    private final UserRepo userRepo;
    private final CharacterRepo characterRepo;
    private final MaxIdService maxIdService;

    public void createCharacter(EzyUser ezyuser, String name, Long model)
    {
        User user = userRepo.findByField("username", ezyuser.getName());
        Character character = new Character();

        double[] startPosition = new double[]{0, 0, 0};
        double[] startRotation = new double[]{0, 0, 0};
        long newcomerRoomId = 1l;


        character.setId(maxIdService.incrementAndGet("character"));
        character.setUserId(user.getId());
        character.setName(name);
        character.setModelId(model);
        character.setRoomId(newcomerRoomId);
        character.setPosition(startPosition);
        character.setRotation(startRotation);
    }

    public List<Character> getAllCharacters(EzyUser ezyuser)
    {
        User user = userRepo.findByField("username", ezyuser.getName());

        return characterRepo.findListByField("userId", user.getId());
    }
}
