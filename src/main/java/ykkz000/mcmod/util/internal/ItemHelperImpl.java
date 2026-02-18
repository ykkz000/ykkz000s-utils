package ykkz000.mcmod.util.internal;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import ykkz000.mcmod.util.api.ItemHelper;

import java.util.function.Function;

public class ItemHelperImpl implements ItemHelper {
    public static ItemHelperImpl create(final String modId) {
        return new ItemHelperImpl(modId);
    }

    private final String modId;

    private ItemHelperImpl(final String modId) {
        this.modId = modId;
    }

    @Override
    public <T extends Item> T register(final String name, final Function<Item.Properties, T> factory, final Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modId, name));
        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties.setId(key)));
    }
}
