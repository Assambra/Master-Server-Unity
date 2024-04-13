package com.assambra.common.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Data;

@Data
@EzyCollection
public class Character {
    @EzyId
    Long id;
    Long accountId;
    String name;
    Long modelId;
    Long roomId;
    double[] position;
    double[] rotation;
}
