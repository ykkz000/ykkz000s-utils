package ykkz000.mcmod.util.internal.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import ykkz000.mcmod.util.api.codec.Data;

public class DataCodec<M extends Data<M, I>, I extends Record & Data.Immutable<I, M>> implements Codec<M> {
    private final Codec<I> immutableCodec;

    protected DataCodec(final Codec<I> immutableCodec) {
        this.immutableCodec = immutableCodec;
    }

    @Override
    public <T> DataResult<Pair<M, T>> decode(DynamicOps<T> ops, T input) {
        return immutableCodec.decode(ops, input).map(pair -> Pair.of(pair.getFirst().toMutable(), pair.getSecond()));
    }

    @Override
    public <T> DataResult<T> encode(M input, DynamicOps<T> ops, T prefix) {
        return immutableCodec.encode(input.toImmutable(), ops, prefix);
    }
}
