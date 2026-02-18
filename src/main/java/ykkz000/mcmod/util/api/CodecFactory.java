package ykkz000.mcmod.util.api;

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
     * Create a codec for a record.
     *
     * @param <T>  The record type.
     * @param type The record type.
     * @return The codec.
     * @apiNote The fields of the record must be int, byte, boolean, short, long, float, double, {@link Integer}, {@link Byte}, {@link Boolean}, {@link Short}, {@link Long}, {@link Float}, {@link Double}, enum, {@link ItemStack}, or array of those.
     */
    <T> Codec<T> codec(final Class<T> type);

    /**
     * Create a stream codec for a record.
     *
     * @param type The record type.
     * @param <T>  The record type.
     * @return The stream codec.
     * @apiNote The fields of the record must be int, byte, boolean, short, long, float, double, {@link Integer}, {@link Byte}, {@link Boolean}, {@link Short}, {@link Long}, {@link Float}, {@link Double}, enum, {@link ItemStack}, or array of those.
     */
    <T extends Record> StreamCodec<RegistryFriendlyByteBuf, T> streamCodec(final Class<T> type);
}
