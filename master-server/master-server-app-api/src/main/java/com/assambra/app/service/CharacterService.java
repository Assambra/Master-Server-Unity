package com.assambra.app.service;

import com.assambra.app.model.CharacterListModel;
import com.assambra.app.model.CharacterModel;
import com.assambra.app.request.CreateCharacterRequest;
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
import java.util.stream.Collectors;

@Setter
@AllArgsConstructor
@EzySingleton("characterService")
public class CharacterService extends EzyLoggable {

    private final UserRepo userRepo;
    private final CharacterRepo characterRepo;
    private final MaxIdService maxIdService;

    public void createCharacter(EzyUser ezyuser, CreateCharacterRequest request)
    {
        User user = userRepo.findByField("username", ezyuser.getName());
        Character character = new Character();

        character.setId(maxIdService.incrementAndGet("character"));
        character.setUserId(user.getId());
        character.setName(request.getName());

        characterRepo.save(character);
    }

    public Boolean characterExist(String name)
    {
        return characterRepo.findByField("name", name) != null;
    }

    public List<Character> getAllCharacters(EzyUser ezyuser)
    {
        User user = userRepo.findByField("username", ezyuser.getName());

        for(Character c : characterRepo.findListByField("userId", user.getId()))
        {
            logger.info("Character: {}", c.getName());
        }

        return characterRepo.findListByField("userId", user.getId());
    }

    public CharacterListModel getCharacterListModel(EzyUser ezyuser)
    {
        List<Character> allCharacters = getAllCharacters(ezyuser);

        List<CharacterModel> characterModel = getCharacterListModel(allCharacters);

        return CharacterListModel.builder()
                .characters(characterModel)
                .build();
    }

    public List<CharacterModel> getCharacterListModel(List<Character> characters)
    {
        List<CharacterModel> answer = characters.stream().map(
                character -> CharacterModel.builder()
                        .id(character.getId())
                        .userId(character.getUserId())
                        .name(character.getName())
                        .build()
        ).collect(Collectors.toList());

        return answer;
    }
}
