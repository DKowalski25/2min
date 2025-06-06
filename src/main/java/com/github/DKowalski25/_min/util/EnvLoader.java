package com.github.DKowalski25._min.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    public static void load() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println("Loaded env: " + entry.getKey() + "=" + entry.getValue());
        });
    }
}
