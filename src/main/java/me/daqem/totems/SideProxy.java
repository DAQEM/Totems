package me.daqem.totems;

import me.daqem.totems.client.ColorHandlers;
import me.daqem.totems.events.EventPlayerChangedDimension;
import me.daqem.totems.events.EventPlayerTick;
import me.daqem.totems.init.ModContainerTypes;
import me.daqem.totems.init.ModItems;
import me.daqem.totems.init.Registration;
import me.daqem.totems.world.OreGeneration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

class SideProxy {
    SideProxy() {
        //Life-cycle events
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(Item.class, ModItems::registerAll);
        modEventBus.addGenericListener(ContainerType.class, ModContainerTypes::registerContainerTypes);

        //Other events
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.register(new EventPlayerTick());
        MinecraftForge.EVENT_BUS.register(new EventPlayerChangedDimension());


        Registration.register();
    }

    static class Client extends SideProxy {
        Client() {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            modEventBus.addListener(ColorHandlers::registerItemColors);
            modEventBus.addListener(ModContainerTypes::registerScreens);
        }
    }

    static class Server extends SideProxy {
        Server() {}
    }
}
