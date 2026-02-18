package ykkz000.mcmod.util.api.client;

import ykkz000.mcmod.util.internal.client.KeyboardHelperImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * The facade of client-side API of ykkz000's Utils.
 *
 * @author ykkz000
 */
public final class UtilsClientApi {
    private static final UtilsClientApi INSTANCE = new UtilsClientApi();

    /**
     * Gets the instance of {@link UtilsClientApi}.
     *
     * @return the instance of {@link UtilsClientApi}. The instance is singleton.
     */
    public static UtilsClientApi instance() {
        return INSTANCE;
    }

    /**
     * Creates an instance of {@link KeyboardHelper}.
     *
     * @param modId the mod ID.
     * @return the instance of {@link KeyboardHelper}. The instance is singleton per mod ID.
     * @apiNote This is a wrapper method of {@link UtilsClientApi#createKeyboardHelper(String)}.
     * @see UtilsClientApi#createKeyboardHelper(String)
     */
    public static KeyboardHelper keyboardHelper(final String modId) {
        return instance().createKeyboardHelper(modId);
    }

    private final Map<String, KeyboardHelper> keyboardHelpers = new HashMap<>();

    private UtilsClientApi() {
    }

    /**
     * Creates an instance of {@link KeyboardHelper}.
     *
     * @param modId the mod ID.
     * @return the instance of {@link KeyboardHelper}. The instance is singleton per mod ID.
     */
    public KeyboardHelper createKeyboardHelper(final String modId) {
        return keyboardHelpers.computeIfAbsent(modId, KeyboardHelperImpl::create);
    }
}
