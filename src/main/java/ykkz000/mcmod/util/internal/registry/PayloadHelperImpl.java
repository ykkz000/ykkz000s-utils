package ykkz000.mcmod.util.internal.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import ykkz000.mcmod.util.api.registry.PayloadHelper;

public class PayloadHelperImpl implements PayloadHelper {
    public static PayloadHelperImpl create(final String modId) {
        return new PayloadHelperImpl(modId);
    }

    private final String modId;

    private PayloadHelperImpl(final String modId) {
        this.modId = modId;
    }

    @Override
    public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> register(final String name, final StreamCodec<RegistryFriendlyByteBuf, T> codec, final boolean serverbound) {
        return (serverbound ? PayloadTypeRegistry.serverboundPlay() : PayloadTypeRegistry.clientboundPlay()).register(new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(modId, name)), codec).type();
    }
}
