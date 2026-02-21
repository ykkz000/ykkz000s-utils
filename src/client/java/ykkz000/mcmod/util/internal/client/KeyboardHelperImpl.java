package ykkz000.mcmod.util.internal.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import ykkz000.mcmod.util.api.client.input.KeyboardHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class KeyboardHelperImpl implements KeyboardHelper {
    public static KeyboardHelperImpl create(final String modId) {
        return new KeyboardHelperImpl(modId);
    }

    private final Map<KeyMapping, Consumer<Minecraft>> keyMappings = new HashMap<>();
    private final String modId;

    private KeyboardHelperImpl(final String modId) {
        this.modId = modId;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            List<KeyMapping> clicks = new ArrayList<>();
            for (final Map.Entry<KeyMapping, Consumer<Minecraft>> entry : keyMappings.entrySet()) {
                boolean clicked = false;
                while (entry.getKey().consumeClick()) {
                    clicked = true;
                }
                if (clicked) {
                    clicks.add(entry.getKey());
                }
            }
            for (final KeyMapping keyMapping : clicks) {
                keyMappings.get(keyMapping).accept(client);
            }
        });
    }

    @Override
    public void register(final String name, final KeyMapping.Category category, final int defaultCode, Consumer<Minecraft> action) {
        keyMappings.put(KeyMappingHelper.registerKeyMapping(new KeyMapping("key." + modId + "." + name, InputConstants.Type.KEYSYM, defaultCode, category)), action);
    }
}
