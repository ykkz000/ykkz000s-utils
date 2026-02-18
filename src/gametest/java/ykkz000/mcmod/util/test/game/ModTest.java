package ykkz000.mcmod.util.test.game;

import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Method;

public class ModTest implements CustomTestMethodInvoker {
    @Override
    public void invokeTestMethod(@NonNull GameTestHelper context, @NonNull Method method) throws ReflectiveOperationException {
        method.invoke(this, context);
    }

    @GameTest
    protected void test1(GameTestHelper context) {
    }
}
