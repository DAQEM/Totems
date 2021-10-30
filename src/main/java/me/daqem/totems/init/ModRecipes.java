package me.daqem.totems.init;

import me.daqem.totems.Totems;
import me.daqem.totems.crafting.recipe.RecolorTotemBagRecipe;
import me.daqem.totems.util.registry.CommonDeferredRegister;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class ModRecipes {

    public static final CommonDeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Totems.MODID);

    public static final RegistryObject<IRecipeSerializer<RecolorTotemBagRecipe>> RECOLOR_TOTEM_BAG = register("recolor_totem_bag", RecolorTotemBagRecipe.Serializer::new);
//    public static final RegistryObject<IRecipeSerializer<TotemBagRecipe>> TOTEM_BAG = register("totem_bag", TotemBagRecipe.Serializer::new);
//    public static final RegistryObject<IRecipeSerializer<TotemOfHealthRecipe>> TOTEM_OF_HEALTH = register("totem_of_health", TotemOfHealthRecipe.Serializer::new);
//    public static final RegistryObject<IRecipeSerializer<TotemOfHealthUpgradeRecipe>> TOTEM_OF_HEALTH_UPGRADE = register("totem_of_health_upgrade", TotemOfHealthUpgradeRecipe.Serializer::new);

    public static void registerMod(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
    }

    private static <T extends IRecipe<?>> RegistryObject<IRecipeSerializer<T>> register(String name, Supplier<IRecipeSerializer<T>> serializerSupplier) {
        return RECIPE_SERIALIZERS.register(name, serializerSupplier);
    }
}
