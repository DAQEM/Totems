package me.daqem.totems.init;

import me.daqem.totems.Totems;
import me.daqem.totems.client.gui.TotemBagContainerScreen;
import me.daqem.totems.inventory.TotemBagContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModContainerTypes {

    public static ContainerType<TotemBagContainer> totem_bag;

    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        totem_bag = register("totem_bag", new ContainerType<>(TotemBagContainer::new));
    }
    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(totem_bag, TotemBagContainerScreen::new);
    }

    private static <T extends Container> ContainerType<T> register(String name, ContainerType<T> type) {
        ResourceLocation id = Totems.getId(name);
        type.setRegistryName(id);
        ForgeRegistries.CONTAINERS.register(type);
        return type;
    }
}
