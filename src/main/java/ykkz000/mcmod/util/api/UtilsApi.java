package ykkz000.mcmod.util.api;

import ykkz000.mcmod.util.internal.ItemHelperImpl;
import ykkz000.mcmod.util.internal.PayloadHelperImpl;
import ykkz000.mcmod.util.internal.CodecFactoryImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * The facade of API of ykkz000's Utils.
 *
 * @author ykkz000
 */
public final class UtilsApi {
    private static final UtilsApi INSTANCE = new UtilsApi();

    /**
     * Gets the instance of {@link UtilsApi}.
     *
     * @return the instance of {@link UtilsApi}. The instance is singleton.
     */
    public static UtilsApi instance() {
        return INSTANCE;
    }

    /**
     * Creates an instance of {@link CodecFactory}.
     *
     * @return the instance of {@link CodecFactory}.
     * @apiNote This is a wrapper method of {@link UtilsApi#createCodecFactory()}.
     * @see UtilsApi#createCodecFactory()
     */
    public static CodecFactory codecFactory() {
        return instance().createCodecFactory();
    }

    /**
     * Creates an instance of {@link ItemHelper}.
     *
     * @param modId the mod ID.
     * @return the instance of {@link ItemHelper}. The instance is singleton per mod ID.
     * @apiNote This is a wrapper method of {@link UtilsApi#createItemHelper(String)}.
     * @see UtilsApi#createItemHelper(String)
     */
    public static ItemHelper itemHelper(final String modId) {
        return instance().createItemHelper(modId);
    }

    /**
     * Creates an instance of {@link PayloadHelper}.
     *
     * @param modId the mod ID.
     * @return the instance of {@link PayloadHelper}. The instance is singleton per mod ID.
     * @apiNote This is a wrapper method of {@link UtilsApi#createPayloadHelper(String)}.
     * @see UtilsApi#createPayloadHelper(String)
     */
    public static PayloadHelper payloadHelper(final String modId) {
        return instance().createPayloadHelper(modId);
    }

    final Map<String, ItemHelper> itemHelpers = new HashMap<>();
    final Map<String, PayloadHelper> payloadHelpers = new HashMap<>();

    private UtilsApi() {
    }

    /**
     * Creates an instance of {@link CodecFactory}.
     *
     * @return the instance of {@link CodecFactory}.
     */
    public CodecFactory createCodecFactory() {
        return CodecFactoryImpl.create();
    }

    /**
     * Creates an instance of {@link ItemHelper}.
     *
     * @param modId the mod ID.
     * @return the instance of {@link ItemHelper}. The instance is singleton per mod ID.
     */
    public ItemHelper createItemHelper(final String modId) {
        return itemHelpers.computeIfAbsent(modId, ItemHelperImpl::create);
    }

    /**
     * Creates an instance of {@link PayloadHelper}.
     *
     * @param modId the mod ID.
     * @return the instance of {@link PayloadHelper}. The instance is singleton per mod ID.
     */
    public PayloadHelper createPayloadHelper(final String modId) {
        return payloadHelpers.computeIfAbsent(modId, PayloadHelperImpl::create);
    }
}
