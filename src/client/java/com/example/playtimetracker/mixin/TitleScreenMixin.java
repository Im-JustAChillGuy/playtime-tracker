package com.example.playtimetracker.mixin;

import com.example.playtimetracker.PlaytimeTrackerClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    // Font is inherited from Screen — shadow it from there
    @Shadow
    protected Font font;

    // FIX: MC 26.x render signature uses DeltaTracker instead of float delta
    @Inject(method = "render", at = @At("TAIL"))
    private void renderPlaytime(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            DeltaTracker deltaTracker,
            CallbackInfo ci
    ) {
        long seconds =
                PlaytimeTrackerClient.DATA.getTicksPlayed() / 20;

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;

        graphics.drawString(
                font,
                "Playtime Tracker",
                5,
                5,
                0xFFFFFF
        );

        graphics.drawString(
                font,
                hours + "h " + minutes + "m",
                5,
                5 + font.lineHeight + 2,
                0xAAAAAA
        );
    }
}
