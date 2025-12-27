package cn.coostack.testapiforjava.listener;

import cn.coostack.cooparticlesapi.annotations.events.EventHandler;
import cn.coostack.cooparticlesapi.annotations.events.EventListener;
import cn.coostack.cooparticlesapi.event.api.EventPriority;
import cn.coostack.cooparticlesapi.event.events.server.ServerPreTickEvent;
import cn.coostack.testapiforjava.TestAPIForJava;
import cn.coostack.testapiforjava.events.CustomCancelableEvent;
import cn.coostack.testapiforjava.events.CustomEvent;
import cn.coostack.testapiforjava.events.CustomInterruptableEvent;
import net.minecraft.text.Text;

/**
 * MODID是 在事件出错时可以快速定位用的 还没想到这些modId的其他用处
 */
@EventListener(modId = TestAPIForJava.MOD_ID)
public class TestListener {

    @EventHandler
    public void onServerTick(ServerPreTickEvent event) {


    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomCalled(CustomEvent event) {
        event.getPlayer().sendMessage(Text.literal("自定义事件 call"));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomCanceledCalled(CustomCancelableEvent event) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(Text.literal("取消事件!"));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomInterruptCalled(CustomInterruptableEvent event) {
        event.setInterrupted(true);
        event.getPlayer().sendMessage(Text.literal("中断事件!"));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCustomInterruptCalledTest(CustomInterruptableEvent event) {
        event.getPlayer().sendMessage(Text.literal("这一段不会执行!"));
    }

}
