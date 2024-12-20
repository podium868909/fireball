package com.podium868909.fireball;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FireballConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public int fireballCooldownTicks = 5;
    public float fireballDivergence = 0F;
    public float fireballSpeed = 2F;
    public int fireballExplosionPower = 2;

    public static FireballConfig loadConfig(File file) {
        FireballConfig config;

        if (file.exists() && file.isFile()) {
            try (
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                config = GSON.fromJson(bufferedReader, FireballConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        } else {
            config = new FireballConfig();
        }

        config.saveConfig(file);

        return config;
    }

    public void saveConfig(File config) {
        try (
            FileOutputStream stream = new FileOutputStream(config);
            Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)
        ) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

}
