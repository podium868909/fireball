package com.podium868909.fireball;

import com.podium868909.fireball.item.fireball;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.File;

public class Fireball implements ModInitializer {

    private static FireballConfig config;

    public static final Identifier FIREBALL_IDENTIFIER = Identifier.of("fireball", "fireball");

    public static final Item FIREBALL =
            Registry.register(Registries.ITEM, FIREBALL_IDENTIFIER, new fireball(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, FIREBALL_IDENTIFIER))));

    @Override
    public void onInitialize() {
        config = FireballConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/fireball.json"));

        PolymerItemGroupUtils.registerPolymerItemGroup(Identifier.of("fireball", "fireball"), ItemGroup.create(ItemGroup.Row.BOTTOM, -1)
                .displayName(Text.literal("Fireball"))
                .icon(FIREBALL::getDefaultStack)
                .entries((displayContext, entries) -> entries.add(FIREBALL)).build());
    }

    public static FireballConfig getConfig() {
        return config;
    }
}
