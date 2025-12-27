package cn.coostack.testapiforjava.events;

import cn.coostack.cooparticlesapi.event.api.CooEvent;
import net.minecraft.entity.player.PlayerEntity;

public class CustomEvent extends CooEvent {
    private final PlayerEntity player;

    public CustomEvent(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
