package com.assambra.app.masterserver.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServerModel {
    private String username;
    private String password;
    private String room;
    private Process process;
}