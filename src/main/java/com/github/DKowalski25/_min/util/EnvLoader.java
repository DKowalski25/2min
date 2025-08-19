package com.github.DKowalski25._min.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    public static void load() {
        if (System.getenv("DOCKER_ENV") == null) {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMalformed()
                    .load();
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                System.out.println("Loaded env: " + entry.getKey() + "=" + entry.getValue());
            });
        } else {
            System.out.println("Running inside Docker — skipping EnvLoader");
        }
    }
}
