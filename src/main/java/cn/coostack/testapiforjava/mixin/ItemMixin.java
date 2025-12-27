package cn.coostack.testapiforjava.mixin;

import cn.coostack.cooparticlesapi.event.CooEventBus;
import cn.coostack.cooparticlesapi.network.particle.emitters.ParticleEmittersManager;
import cn.coostack.cooparticlesapi.network.particle.style.ParticleStyleManager;
import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudEffect;
import cn.coostack.cooparticlesapi.utils.Math3DUtil;
import cn.coostack.testapiforjava.events.CustomCancelableEvent;
import cn.coostack.testapiforjava.events.CustomEvent;
import cn.coostack.testapiforjava.events.CustomInterruptableEvent;
import cn.coostack.testapiforjava.test.TestEmitter;
import cn.coostack.testapiforjava.test.TestShapeStyle;
import cn.coostack.testapiforjava.test.TestStyle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At("HEAD"))
    public void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        var emitter = new TestEmitter(user.getEyePos(), user.getWorld());
        emitter.setMaxTick(50);
        emitter.templateData.setEffect(
                new ControlableCloudEffect(emitter.templateData.getUuid(), false)
        );
        emitter.templateData.setColor(
                Math3DUtil.INSTANCE.colorOf(255, 255, 0)
        );
        emitter.templateData.setMaxAge(10);
//        ParticleEmittersManager.INSTANCE.spawnEmitters(emitter);


        ParticleStyleManager.INSTANCE.spawnStyle(
                user.getWorld(),
                user.getEyePos(),
                new TestShapeStyle(UUID.randomUUID())
        );


        // call custom event
        var custom = new CustomEvent(user);
        CooEventBus.call(custom);
        var customCanceled = new CustomCancelableEvent(user);
        user.sendMessage(
                Text.literal("事件执行后 是否取消: " + CooEventBus.call(customCanceled).isCancelled())
        );

        var customInterrupted = new CustomInterruptableEvent(user);
        user.sendMessage(
                Text.literal("事件执行后 是否中断: " + CooEventBus.call(customInterrupted).isInterrupted())
        );
    }
}
