package com.sti.carservice.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageKey {

    //Not found.
    CAR_NOT_FOUND("car-not-found");

    public final String key;

    public String getKey() {
        return this.key;
    }

}
