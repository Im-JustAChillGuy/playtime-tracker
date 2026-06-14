package com.example.playtimetracker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class PlaytimeTrackerClient implements ClientModInitializer {

    public static PlaytimeData DATA;

    private static int saveTimer;

    @Override
    public void onInitializeClient() {

        DATA = PlaytimeData.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null || client.isPaused()) {
                return;
            }

            DATA.addTick();

            saveTimer++;

            if (saveTimer >= 600) { // 30 seconds
                DATA.save();
                saveTimer = 0;
            }
        });

        ClientCommandRegistrationCallback.EVENT.register(
                (dispatcher, context) ->
                        PlaytimeCommand.register(dispatcher)
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    if (DATA != null) {
                        DATA.save();
                    }
                })
        );
    }
}
