package me.daqem.totems.events;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventPlayerChangedDimension {

    @SubscribeEvent
    public void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer().getPersistentData().getBoolean("totems_allow_flight")) EventPlayerTick.preventFlight(event.getPlayer());
    }
}
