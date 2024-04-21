package com.assambra.common.masterserver.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

import java.util.Map;

@Data
@EzyObjectBinding
@SuppressWarnings("MemberName")
public class ServerToClientRequest {
    private String recipient;
    private String command;
    private Map<String, Object> additionalParams;
}
