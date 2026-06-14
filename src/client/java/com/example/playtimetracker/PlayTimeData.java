package com.example.playtimetracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class PlaytimeData {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Path FILE =
            Path.of("config", "playtimetracker.json");

    private long ticksPlayed;

    public long getTicksPlayed() {
        return ticksPlayed;
    }

    public void addTick() {
        ticksPlayed++;
    }

    public static PlaytimeData load() {
        try {
            if (Files.exists(FILE)) {
                return GSON.fromJson(
                        Files.readString(FILE),
                        PlaytimeData.class
                );
            }
        } catch (Exception ignored) {
        }

        return new PlaytimeData();
    }

    public void save() {
        try {
            Files.createDirectories(FILE.getParent());

            Files.writeString(
                    FILE,
                    GSON.toJson(this)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
