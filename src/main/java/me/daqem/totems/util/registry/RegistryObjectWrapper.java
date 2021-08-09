package me.daqem.totems.util.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;
import java.util.function.Supplier;

public class RegistryObjectWrapper<T extends IForgeRegistryEntry<? super T>> implements Supplier<T> {
    protected final RegistryObject<T> registryObject;

    public RegistryObjectWrapper(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public T get() {
        return registryObject.get();
    }

    public ResourceLocation getId() {
        return registryObject.getId();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof RegistryObjectWrapper) {
            return Objects.equals(((RegistryObjectWrapper<?>) obj).getId(), getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
