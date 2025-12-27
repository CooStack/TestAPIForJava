package cn.coostack.testapiforjava.test;


import cn.coostack.cooparticlesapi.annotations.emitter.EmitterAutoRegister;
import cn.coostack.cooparticlesapi.annotations.emitter.EmitterField;
import cn.coostack.cooparticlesapi.annotations.emitter.handle.ParticleEmittersHelper;
import cn.coostack.cooparticlesapi.extend.Vec3ExtendsKt;
import cn.coostack.cooparticlesapi.network.particle.emitters.ClassParticleEmitters;
import cn.coostack.cooparticlesapi.network.particle.emitters.ControlableParticleData;
import cn.coostack.cooparticlesapi.network.particle.emitters.ParticleEmitters;
import cn.coostack.cooparticlesapi.particles.ControlableParticle;
import cn.coostack.cooparticlesapi.particles.control.ParticleControler;
import cn.coostack.cooparticlesapi.utils.PhysicsUtil;
import cn.coostack.cooparticlesapi.utils.RelativeLocation;
import cn.coostack.cooparticlesapi.utils.builder.PointsBuilder;
import kotlin.Pair;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

// 使用这个可以自动注册粒子发射器
@EmitterAutoRegister
public class TestEmitter extends ClassParticleEmitters {
    public TestEmitter() {
        super(Vec3d.ZERO, null);
    }

    @EmitterField
    public ControlableParticleData templateData = new ControlableParticleData();
    Random random = new Random();

    /**
     * 要使用自动生成codec 需要提供这样的构造函数或者空的构造函数
     */
    public TestEmitter(Vec3d pos, World world) {
        super(pos, world);
    }

    @Override
    public void singleParticleAction(@NotNull ParticleControler controler, @NotNull ControlableParticleData controlableParticleData, @NotNull RelativeLocation relativeLocation, @NotNull World world, float v, float v1) {
        controlableParticleData.setMaxAge(random.nextInt(10) + controlableParticleData.getMaxAge());
        controlableParticleData.setVelocity(
                Vec3ExtendsKt.randomVec3().multiply(random.nextDouble(0.3))
        );
        controler.addPreTickAction((particle) -> {
            updatePhysics(particle.getLoc(), controlableParticleData, particle);
            return null;
        });
    }

    /**
     * 可选重写
     * 对粒子移动的二次操作 一般用于物理操作
     */
    @Override
    protected void moveSingleParticleWithVelocity(@NotNull ControlableParticle particle,
                                                  @NotNull ControlableParticleData data,
                                                  @NotNull Vec3d to,
                                                  @NotNull BlockHitResult collide) {
        if (collide.getType() != HitResult.Type.MISS) {
            data.setVelocity(
                    PhysicsUtil.INSTANCE.collideMovement(collide, data.getVelocity())
            );
            Vec3d toPosFixed = PhysicsUtil.INSTANCE.fixBeforeCollidePosition(collide);
            particle.teleportTo(toPosFixed);
            return;
        }
        // 移动粒子的方法
        particle.teleportTo(to);
    }

    @Override
    public void doTick() {

    }

    @Override
    public @NotNull List<Pair<ControlableParticleData, RelativeLocation>> genParticles(float deltaTick) {
        return PointsBuilder.of(List.of())
                .addBall(1.0, 16)
                .create()
                .stream()
                .map((rel) -> {
                    return new Pair<ControlableParticleData, RelativeLocation>(templateData.clone(), rel);
                }).toList();
    }

    @Override
    public @NotNull String getEmittersID() {
        return "test-emitter";
    }

    @Override
    public @NotNull PacketCodec<PacketByteBuf, ParticleEmitters> getCodec() {
        /**
         * 自动生成Codec 或者自己创建一个Codec也可以 （Static）
         */
        return ParticleEmittersHelper.INSTANCE.generateCodec(this);
    }
}