package me.daqem.totems.init;

import me.daqem.totems.Totems;
import me.daqem.totems.item.SimpleItem;
import me.daqem.totems.item.TotemBagItem;
import me.daqem.totems.item.TotemItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final TotemBagItem totemBag = new TotemBagItem();
    public static final SimpleItem totemGem = new SimpleItem();
    public static final SimpleItem totemScrap = new SimpleItem();
    public static final SimpleItem heart = new SimpleItem();

    public static final TotemItem totemOfHealth = new TotemItem();
    public static final TotemItem totemOfArmor = new TotemItem();
    public static final TotemItem totemOfHunger = new TotemItem();
    public static final TotemItem totemOfSpeed = new TotemItem();
    public static final TotemItem totemOfStrength = new TotemItem();
    public static final TotemItem totemOfHaste = new TotemItem();
    public static final TotemItem totemOfTreeFelling = new TotemItem();
    public static final TotemItem totemOfFlight = new TotemItem();
    public static final TotemItem totemOfVeinMining = new TotemItem();



    public static void registerAll(RegistryEvent.Register<Item> event) {

        register("totem_bag", totemBag);
        register("totem_gem", totemGem);
        register("totem_scrap", totemScrap);
        register("heart", heart);

        register("totem_of_health", totemOfHealth);
        register("totem_of_armor", totemOfArmor);
        register("totem_of_hunger", totemOfHunger);
        register("totem_of_speed", totemOfSpeed);
        register("totem_of_strength", totemOfStrength);
        register("totem_of_haste", totemOfHaste);
        register("totem_of_tree_felling", totemOfTreeFelling);
        register("totem_of_flight", totemOfFlight);
        register("totem_of_vein_mining", totemOfVeinMining);
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = Totems.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
