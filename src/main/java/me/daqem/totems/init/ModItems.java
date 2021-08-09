package me.daqem.totems.init;

import me.daqem.totems.Totems;
import me.daqem.totems.item.HealthTotemItem;
import me.daqem.totems.item.SimpleItem;
import me.daqem.totems.item.TotemBagItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final TotemBagItem totemBag = new TotemBagItem();
    public static final HealthTotemItem healthTotem = new HealthTotemItem();

    public static final SimpleItem totemGem = new SimpleItem();
    public static final SimpleItem totemScrap = new SimpleItem();
    public static final SimpleItem heart = new SimpleItem();

    public static void registerAll(RegistryEvent.Register<Item> event) {

        register("totem_bag", totemBag);
        register("health_totem", healthTotem);
        register("totem_gem", totemGem);
        register("totem_scrap", totemScrap);
        register("heart", heart);
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = Totems.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
