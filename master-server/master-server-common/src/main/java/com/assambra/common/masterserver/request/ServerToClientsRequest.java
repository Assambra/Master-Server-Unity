package com.assambra.common.masterserver.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Data;

import java.util.Map;

@Data
@EzyObjectBinding
@SuppressWarnings("MemberName")
public class ServerToClientsRequest {
    private EzyArray recipients;
    private String command;
    private Map<String, Object> additionalParams;
}
