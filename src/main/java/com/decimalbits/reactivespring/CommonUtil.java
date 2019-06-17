package com.decimalbits.reactivespring;


import java.util.UUID;
import java.util.function.Supplier;

public class CommonUtil {

    private CommonUtil(){}

    public static final Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
}
