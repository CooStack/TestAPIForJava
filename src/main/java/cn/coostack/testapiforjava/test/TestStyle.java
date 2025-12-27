package cn.coostack.testapiforjava.test;

import cn.coostack.cooparticlesapi.network.buffer.ParticleControlerDataBuffer;
import cn.coostack.cooparticlesapi.network.particle.style.ParticleGroupStyle;
import cn.coostack.cooparticlesapi.network.particle.style.ParticleStyleProvider;
import cn.coostack.cooparticlesapi.utils.RelativeLocation;
import cn.coostack.cooparticlesapi.utils.builder.PointsBuilder;
import cn.coostack.cooparticlesapi.utils.helper.buffer.ControlableBufferHelper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestStyle extends ParticleGroupStyle {
    public TestStyle(UUID uuid) {
        super(256.0, uuid);
    }

    public static class Provider implements ParticleStyleProvider {
        @Override
        public @NotNull ParticleGroupStyle createStyle(@NotNull UUID uuid, @NotNull Map<String, ? extends ParticleControlerDataBuffer<?>> map) {
            return new TestStyle(uuid);
        }
    }

    @Override
    public @NotNull Map<StyleData, RelativeLocation> getCurrentFrames() {
        return PointsBuilder.of(List.of())
                .addBall(2.0, 16)
                .createWithStyleData((r) -> {
                    return new StyleDataBuilder()
                            .addParticleControlerHandler((cont) -> {
                                cont.setInitInvoker((particle) -> {
                                    particle.setColor(new Vector3f(1f, 1f, 0f));
                                    return null;
                                });
                                return null;
                            })
                            .build();
                });
    }

    @Override
    public void onDisplay() {
        addPreTickAction((style) -> {
            style.rotateParticlesAsAxis(Math.PI / 16);
            return null;
        });
    }

    @Override
    public @NotNull Map<String, ParticleControlerDataBuffer<?>> writePacketArgs() {
        return ControlableBufferHelper.INSTANCE.getPairs(this);
    }

    @Override
    public void readPacketArgs(@NotNull Map<String, ? extends ParticleControlerDataBuffer<?>> map) {
        ControlableBufferHelper.INSTANCE.setPairs(this, map);
    }
}
