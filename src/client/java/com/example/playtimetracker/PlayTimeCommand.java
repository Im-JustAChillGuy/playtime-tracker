package com.example.playtimetracker;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

public final class PlaytimeCommand {

    public static void register(
            CommandDispatcher<FabricClientCommandSource> dispatcher
    ) {

        dispatcher.register(
                ClientCommandManager.literal("playtime")
                        .executes(context -> {

                            long seconds =
                                    PlaytimeTrackerClient.DATA.getTicksPlayed() / 20;

                            long hours = seconds / 3600;
                            long minutes = (seconds % 3600) / 60;
                            long secs = seconds % 60;

                            context.getSource().sendFeedback(
                                    Component.literal(
                                            String.format(
                                                    "Total Playtime: %dh %dm %ds",
                                                    hours,
                                                    minutes,
                                                    secs
                                            )
                                    )
                            );

                            return 1;
                        })
        );
    }

    private PlaytimeCommand() {}
}
