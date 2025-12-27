package cn.coostack.testapiforjava.events;

import cn.coostack.cooparticlesapi.event.api.CooEvent;
import cn.coostack.cooparticlesapi.event.api.EventCancelable;
import net.minecraft.entity.player.PlayerEntity;

/**
 * 可取消事件 （需要自己在call之后进行判断cancel）
 */
public class CustomCancelableEvent extends CooEvent implements EventCancelable {
    private final PlayerEntity player;
    private boolean cancelled;

    public CustomCancelableEvent(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
