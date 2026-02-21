package ykkz000.mcmod.util.test;

import net.minecraft.SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ykkz000.mcmod.util.api.registry.ItemHelper;
import ykkz000.mcmod.util.api.UtilsApi;

import java.util.Optional;

public class ItemHelperTest {
    private static final String NAMESPACE = "ykkz000s-utils-test";
    private ItemHelper itemHelper = null;

    @BeforeAll
    protected static void setupAll() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @BeforeEach
    protected void setupEach() {
        itemHelper = UtilsApi.itemHelper(NAMESPACE);
    }

    @Test
    protected void testRegisterItem() {
        final String ID = "test-item";
        Assertions.assertNotNull(itemHelper);
        Assertions.assertNotNull(itemHelper.register(ID, Item::new));
        Assertions.assertTrue(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath(NAMESPACE, ID)));
        Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(NAMESPACE, ID));
        Assertions.assertTrue(item.isPresent());
        Assertions.assertTrue(item.get().isBound());
    }

    @Test
    protected void testRegisterItemWithProperties() {
        final String ID = "test-item-with-properties";
        Assertions.assertNotNull(itemHelper);
        Assertions.assertNotNull(itemHelper.register(ID, Item::new, new Item.Properties().stacksTo(31)));
        Assertions.assertTrue(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath(NAMESPACE, ID)));
        Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(NAMESPACE, ID));
        Assertions.assertTrue(item.isPresent());
        Assertions.assertTrue(item.get().isBound());
    }

    @Test
    protected void testRegisterItemFromBlock() {
        final String ID = BuiltInRegistries.BLOCK.getKey(Blocks.AIR).getPath();
        Assertions.assertNotNull(itemHelper);
        Assertions.assertNotNull(itemHelper.registerFromBlock(Blocks.AIR));
        Assertions.assertTrue(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath(NAMESPACE, ID)));
        Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(NAMESPACE, ID));
        Assertions.assertTrue(item.isPresent());
        Assertions.assertTrue(item.get().isBound());
    }

    @Test
    protected void testRegisterItemFromBlockWithProperties() {
        final String ID = BuiltInRegistries.BLOCK.getKey(Blocks.GRASS_BLOCK).getPath();
        Assertions.assertNotNull(itemHelper);
        Assertions.assertNotNull(itemHelper.registerFromBlock(Blocks.GRASS_BLOCK, new Item.Properties().stacksTo(53)));
        Assertions.assertTrue(BuiltInRegistries.ITEM.containsKey(Identifier.fromNamespaceAndPath(NAMESPACE, ID)));
        Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(Identifier.fromNamespaceAndPath(NAMESPACE, ID));
        Assertions.assertTrue(item.isPresent());
        Assertions.assertTrue(item.get().isBound());
    }
}
