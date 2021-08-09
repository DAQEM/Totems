package me.daqem.totems.data;

import me.daqem.totems.init.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModItems.totemScrap, 1)
                .patternLine(" G ")
                .patternLine("TDT")
                .patternLine(" G ")
                .key('T', ModItems.totemGem)
                .key('D', Items.DIAMOND)
                .key('G', Items.GOLD_INGOT)
                .addCriterion("totem_gem", InventoryChangeTrigger.Instance.forItems(ModItems.totemGem))
                .build(consumer);
    }
}
