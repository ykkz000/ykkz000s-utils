package ykkz000.mcmod.util.api.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.function.Consumer;

/**
 * A helper for keyboard registration.
 *
 * @author ykkz000
 */
public interface KeyboardHelper {
    /**
     * Registers a key.
     *
     * @param name        the name of the key
     * @param category    the category of the key
     * @param defaultCode the default code of the key
     * @param action      the action of the key when the key is clicked
     */
    void register(final String name, final KeyMapping.Category category, final int defaultCode, Consumer<Minecraft> action);
}
