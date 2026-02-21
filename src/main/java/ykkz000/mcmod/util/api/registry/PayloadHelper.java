package ykkz000.mcmod.util.api.registry;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * A helper for payload registration.
 *
 * @author ykkz000
 */
public interface PayloadHelper {
    /**
     * Registers a serverbound payload.
     *
     * @param name  the name
     * @param codec the codec
     * @param <T>   the type
     * @return the type
     */
    default <T extends CustomPacketPayload> CustomPacketPayload.Type<T> registerServerbound(final String name, final StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        return register(name, codec, true);
    }

    /**
     * Registers a clientbound payload.
     *
     * @param name  the name
     * @param codec the codec
     * @param <T>   the type
     * @return the type
     */
    default <T extends CustomPacketPayload> CustomPacketPayload.Type<T> registerClientbound(final String name, final StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        return register(name, codec, false);
    }

    /**
     * Registers a payload.
     *
     * @param name        the name
     * @param codec       the codec
     * @param serverbound whether the payload is serverbound
     * @param <T>         the type
     * @return the type
     */
    <T extends CustomPacketPayload> CustomPacketPayload.Type<T> register(final String name, final StreamCodec<RegistryFriendlyByteBuf, T> codec, final boolean serverbound);
}
