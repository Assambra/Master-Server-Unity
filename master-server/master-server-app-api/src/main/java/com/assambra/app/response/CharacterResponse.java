package com.assambra.app.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@EzyObjectBinding
public class CharacterResponse {
    private Long id;
    private Long userId;
    private String name;
}
