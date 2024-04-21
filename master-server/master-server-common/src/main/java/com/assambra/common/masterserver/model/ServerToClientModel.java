package com.assambra.common.masterserver.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ServerToClientModel {
    private Map<String, Object> additionalParams;
}
