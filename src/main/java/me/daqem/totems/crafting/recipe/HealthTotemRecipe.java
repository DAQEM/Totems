package me.daqem.totems.crafting.recipe;

import com.google.gson.JsonObject;
import me.daqem.totems.init.ModItems;
import me.daqem.totems.item.HealthTotemItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Map;

public class HealthTotemRecipe extends ShapedRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public HealthTotemRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
        super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int scrapCount = 0;
        int heartCount = 0;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.totemScrap) {
                    ++scrapCount;
                } else if (stack.getItem() == ModItems.heart) {
                    ++heartCount;
                } else {
                    return false;
                }
            }
        }
        return scrapCount == 2 && heartCount == 1;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stack = new ItemStack(ModItems.healthTotem);
        ((HealthTotemItem) stack.getItem()).setLevel(stack, 1);
        stack.setDisplayName(new KeybindTextComponent(TextFormatting.YELLOW + "Health Totem"));
        return stack.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HealthTotemRecipe> {

        @Override
        public HealthTotemRecipe read(ResourceLocation recipeId, JsonObject json) {
            final String group = JSONUtils.getString(json, "group", "");
            final Map<String, Ingredient> keys = deserializeKey(JSONUtils.getJsonObject(json, "key"));
            final String[] pattern = shrink(patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
            final int width = pattern[0].length();
            final int height = pattern.length;
            final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, keys, width, height);
            final ItemStack output = deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new HealthTotemRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public HealthTotemRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final int width = buffer.readVarInt();
            final int height = buffer.readVarInt();
            final String group = buffer.readString(32767);
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (int index = 0; index < ingredients.size(); ++index) {
                ingredients.set(index, Ingredient.read(buffer));
            }

            final ItemStack output = buffer.readItemStack();
            return new HealthTotemRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketBuffer buffer, HealthTotemRecipe recipe) {
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());
            buffer.writeString(recipe.getGroup());

            for (final Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.getRecipeOutput());
        }
    }
}
