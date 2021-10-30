package me.daqem.totems.init;

import me.daqem.totems.Totems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Consumer;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS);


    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registerMod(ModRecipes::registerMod);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        ModBlocks.register();
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, Totems.MODID);
    }

    public static void registerMod(Consumer<IEventBus> consumer) {
        register(Mod.EventBusSubscriber.Bus.MOD, consumer);
    }

    public static void registerForge(Consumer<IEventBus> consumer) {
        register(Mod.EventBusSubscriber.Bus.FORGE, consumer);
    }

    public static void register(Mod.EventBusSubscriber.Bus bus, Consumer<IEventBus> consumer) {
        register(bus.bus().get(), consumer);
    }

    public static void register(IEventBus bus, Consumer<IEventBus> consumer) {
        consumer.accept(bus);
    }
}
