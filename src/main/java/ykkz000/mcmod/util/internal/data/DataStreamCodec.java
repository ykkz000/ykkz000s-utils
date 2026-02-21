package ykkz000.mcmod.util.internal.data;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import ykkz000.mcmod.util.api.codec.Data;

public class DataStreamCodec<M extends Data<M, I>, I extends Record & Data.Immutable<I, M>> implements StreamCodec<RegistryFriendlyByteBuf, M> {
    private final StreamCodec<RegistryFriendlyByteBuf, I> immutableStreamCodec;

    protected DataStreamCodec(final StreamCodec<RegistryFriendlyByteBuf, I> immutableStreamCodec) {
        this.immutableStreamCodec = immutableStreamCodec;
    }

    @Override
    public M decode(RegistryFriendlyByteBuf input) {
        return immutableStreamCodec.decode(input).toMutable();
    }

    @Override
    public void encode(RegistryFriendlyByteBuf output, M value) {
        immutableStreamCodec.encode(output, value.toImmutable());
    }
}
