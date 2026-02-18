package ykkz000.mcmod.util.api;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A helper for item registration.
 *
 * @author ykkz000
 */
public interface ItemHelper {
    /**
     * Registers an item.
     *
     * @param name    the name of the item
     * @param factory the factory of the item
     * @param <T>     the type of the item
     * @return the item
     */
    default <T extends Item> T register(final String name, final Function<Item.Properties, T> factory) {
        return register(name, factory, new Item.Properties());
    }

    /**
     * Registers an item.
     *
     * @param name       the name of the item
     * @param factory    the factory of the item
     * @param properties the properties of the item
     * @param <T>        the type of the item
     * @return the item
     */
    <T extends Item> T register(final String name, final Function<Item.Properties, T> factory, final Item.Properties properties);

    /**
     * Registers an item from a block.
     *
     * @param block the block
     * @return the item
     */
    default BlockItem registerFromBlock(final Block block) {
        return registerFromBlock(block, new Item.Properties());
    }

    /**
     * Registers an item from a block.
     *
     * @param block      the block
     * @param properties the properties of the item
     * @return the item
     */
    default BlockItem registerFromBlock(final Block block, final Item.Properties properties) {
        return register(BuiltInRegistries.BLOCK.getKey(block).getPath(), p -> new BlockItem(block, p), properties);
    }
}
