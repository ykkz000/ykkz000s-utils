package ykkz000.mcmod.util.api.codec;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

/**
 * A factory for codec.
 *
 * @author ykkz000
 */
public interface CodecFactory {
    /**
     * Create a codec for a data.
     *
     * @param type The data type.
     * @param <M>  The data type.
     * @param <I>  The immutable type.
     * @return The codec.
     * @apiNote The fields of the record must be int, byte, boolean, short, long, float, double, {@link Integer}, {@link Byte}, {@link Boolean}, {@link Short}, {@link Long}, {@link Float}, {@link Double}, {@link String}, enum, {@link ItemStack}, another record, or array of those.
     */
    <M extends Data<M, I>, I extends Record & Data.Immutable<I, M>> Codec<M> codec(Class<M> type);

    /**
     * Create a codec for a record.
     *
     * @param <T>  The record type.
     * @param type The record type.
     * @return The codec.
     * @apiNote The fields of the record must be int, byte, boolean, short, long, float, double, {@link Integer}, {@link Byte}, {@link Boolean}, {@link Short}, {@link Long}, {@link Float}, {@link Double}, {@link String}, enum, {@link ItemStack}, another record, or array of those.
     */
    <T extends Record> Codec<T> recordCodec(final Class<T> type);

    /**
     * Create a stream codec for a data.
     *
     * @param type The data type.
     * @param <M>  The data type.
     * @param <I>  The immutable type.
     * @return The stream codec.
     * @apiNote The fields of the record must be int, byte, boolean, short, long, float, double, {@link Integer}, {@link Byte}, {@link Boolean}, {@link Short}, {@link Long}, {@link Float}, {@link Double}, {@link String}, enum, {@link ItemStack}, another record, or array of those.
     */
    <M extends Data<M, I>, I extends Record & Data.Immutable<I, M>> StreamCodec<RegistryFriendlyByteBuf, M> streamCodec(final Class<M> type);

    /**
     * Create a stream codec for a record.
     *
     * @param type The record type.
     * @param <T>  The record type.
     * @return The stream codec.
     * @apiNote The fields of the record must be int, byte, boolean, short, long, float, double, {@link Integer}, {@link Byte}, {@link Boolean}, {@link Short}, {@link Long}, {@link Float}, {@link Double}, {@link String}, enum, {@link ItemStack}, another record, or array of those.
     */
    <T extends Record> StreamCodec<RegistryFriendlyByteBuf, T> recordStreamCodec(final Class<T> type);
}
