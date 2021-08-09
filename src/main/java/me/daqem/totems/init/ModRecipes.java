package me.daqem.totems.init;

import me.daqem.totems.Totems;
import me.daqem.totems.crafting.recipe.RecolorTotemBagRecipe;
import me.daqem.totems.crafting.recipe.TotemBagRecipe;
import me.daqem.totems.util.registry.CommonDeferredRegister;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    public static final CommonDeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Totems.MODID);

    public static final RegistryObject<IRecipeSerializer<RecolorTotemBagRecipe>> RECOLOR_TOTEM_BAG = RECIPE_SERIALIZERS.register("recolor_totem_bag", RecolorTotemBagRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<TotemBagRecipe>> TOTEM_BAG = RECIPE_SERIALIZERS.register("totem_bag", TotemBagRecipe.Serializer::new);

    public static void registerMod(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
    }
}
