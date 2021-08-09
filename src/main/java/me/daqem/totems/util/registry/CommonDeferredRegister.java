package me.daqem.totems.util.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

public class CommonDeferredRegister<R extends IForgeRegistryEntry<R>> implements Iterable<RegistryObject<R>> {

    public static <C extends IForgeRegistryEntry<C>> CommonDeferredRegister<C> create(IForgeRegistry<C> registry, String modid) {
        return new CommonDeferredRegister<>(registry, modid);
    }

    private final DeferredRegister<R> register;

    protected CommonDeferredRegister(IForgeRegistry<R> registry, String modid) {
        register = DeferredRegister.create(registry, modid);
    }

    public <E extends R> RegistryObject<E> register(String name, Supplier<? extends E> supplier) {
        return register.register(name, supplier);
    }

    public void register(IEventBus bus) {
        register.register(bus);
    }

    public Collection<RegistryObject<R>> getEntries() {
        return register.getEntries();
    }

    @Override
    public Iterator<RegistryObject<R>> iterator() {
        return getEntries().iterator();
    }
}