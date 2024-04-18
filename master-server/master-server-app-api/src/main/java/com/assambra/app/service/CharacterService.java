package com.assambra.app.service;

import com.assambra.app.constant.GameConstant;
import com.assambra.app.model.CharacterInfoListModel;
import com.assambra.app.model.CharacterInfoModel;
import com.assambra.app.model.CharacterListModel;
import com.assambra.app.model.CharacterModel;
import com.assambra.app.request.CreateCharacterRequest;
import com.assambra.common.entity.Character;
import com.assambra.common.entity.CharacterLocation;
import com.assambra.common.entity.User;
import com.assambra.common.repo.CharacterLocationRepo;
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

    private final MaxIdService maxIdService;
    private final UserRepo userRepo;
    private final CharacterRepo characterRepo;
    private final CharacterLocationRepo characterLocationRepo;

    public Character getCharacter(Long id)
    {
        return characterRepo.findById(id);
    }

    public void createCharacter(EzyUser ezyuser, CreateCharacterRequest request)
    {
        User user = userRepo.findByField("username", ezyuser.getName());

        Character character = new Character();
        character.setId(maxIdService.incrementAndGet("character"));
        character.setUserId(user.getId());
        character.setName(request.getName());
        character.setUsername(user.getUsername());
        characterRepo.save(character);

        CharacterLocation characterLocation = new CharacterLocation();
        characterLocation.setId(maxIdService.incrementAndGet("characterLocation"));
        characterLocation.setCharacterId(character.getId());
        characterLocation.setRoomName(GameConstant.START_SCENE);
        characterLocation.setPosition(GameConstant.START_POSITION);
        characterLocation.setRotation(GameConstant.START_ROTATION);
        characterLocationRepo.save(characterLocation);
    }

    public Boolean characterExist(String name)
    {
        return characterRepo.findByField("name", name) != null;
    }

    public List<Character> getAllCharacters(EzyUser ezyuser)
    {
        User user = userRepo.findByField("username", ezyuser.getName());

        return characterRepo.findListByField("userId", user.getId());
    }

    public CharacterInfoListModel getCharacterInfoListModel(EzyUser ezyuser)
    {
        List<Character> allCharacters = getAllCharacters(ezyuser);

        List<CharacterInfoModel> characterInfoModel = getListCharacterInfoModel(allCharacters);

        return CharacterInfoListModel.builder()
                .characters(characterInfoModel)
                .build();
    }

    public List<CharacterInfoModel> getListCharacterInfoModel(List<Character> characters)
    {
        List<CharacterInfoModel> answer = characters.stream().map(
                character -> CharacterInfoModel.builder()
                        .id(character.getId())
                        .name(character.getName())
                        .build()
        ).collect(Collectors.toList());

        return answer;
    }

    public CharacterListModel getCharacterListModel(EzyUser ezyuser)
    {
        List<Character> allCharacters = getAllCharacters(ezyuser);

        List<CharacterModel> characterModel = getListCharacterModel(allCharacters);

        return CharacterListModel.builder()
                .characters(characterModel)
                .build();
    }

    public List<CharacterModel> getListCharacterModel(List<Character> characters)
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

    public CharacterLocation getCharacterLocation(Long characterId)
    {
        return characterLocationRepo.findByField("characterId", characterId);
    }
}
