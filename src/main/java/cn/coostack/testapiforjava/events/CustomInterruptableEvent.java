package cn.coostack.testapiforjava.events;

import cn.coostack.cooparticlesapi.event.api.CooEvent;
import cn.coostack.cooparticlesapi.event.api.EventInterruptible;
import net.minecraft.entity.player.PlayerEntity;

/**
 * 可中断事件
 * 一旦 interrupt = true 则不会执行后续的事件
 */
public class CustomInterruptableEvent extends CooEvent implements EventInterruptible {
    private final PlayerEntity player;
    private boolean interrupt;

    public CustomInterruptableEvent(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public boolean isInterrupted() {
        return interrupt;
    }

    @Override
    public void setInterrupted(boolean b) {
        interrupt = b;
    }
}
