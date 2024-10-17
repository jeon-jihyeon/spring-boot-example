package com.example.spring.boot.core;

import com.github.f4b6a3.tsid.TsidCreator;

public class IdGenerator {
    public static Long newId() {
        return TsidCreator.getTsid().toLong();
    }
}
