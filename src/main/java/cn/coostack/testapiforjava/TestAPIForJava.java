package cn.coostack.testapiforjava;

import cn.coostack.cooparticlesapi.network.particle.style.ParticleStyleManager;
import cn.coostack.cooparticlesapi.reflect.CooAPIScanner;
import cn.coostack.testapiforjava.test.TestShapeStyle;
import cn.coostack.testapiforjava.test.TestStyle;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric yarn + java 使用 CooParticlesAPI Demo
 *
 * @author CooStack
 */
public class TestAPIForJava implements ModInitializer {
    public static final String MOD_ID = "testapiforjava";

    @Override
    public void onInitialize() {
        // 添加扫描器 （节约扫描空间） 如果你的包不是以cn.coostack开头的那必须加 不然所有事件和自动注册都会失效
        CooAPIScanner.registerPacket(TestAPIForJava.class);
        ParticleStyleManager.INSTANCE.register(TestStyle.class, new TestStyle.Provider());
        ParticleStyleManager.INSTANCE.register(TestShapeStyle.class, new TestShapeStyle.Provider());
    }
}
