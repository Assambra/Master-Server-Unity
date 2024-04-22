package com.assambra.common.repo;

import com.assambra.common.entity.CharacterLocation;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository("characterLocationRepo")
public interface CharacterLocationRepo extends EzyMongoRepository<Long, CharacterLocation> {
}
