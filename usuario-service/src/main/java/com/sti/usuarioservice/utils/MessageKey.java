package com.sti.usuarioservice.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageKey {

    //Not found.
    USER_NOT_FOUND("user-not-found");

    public final String key;

    public String getKey(){
        return this.key;
    }

}
