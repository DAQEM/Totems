package me.daqem.totems.crafting.recipe;

import com.google.gson.JsonObject;
import me.daqem.totems.Totems;
import me.daqem.totems.init.ModItems;
import me.daqem.totems.item.TotemItem;
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

public class TotemOfHealthRecipe extends ShapedRecipe {

    public static final ResourceLocation NAME = Totems.getId("totem_of_health");
    public static final Serializer SERIALIZER = new Serializer();

    public TotemOfHealthRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
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
        ItemStack stack = new ItemStack(ModItems.totemOfHealth);
        ((TotemItem) stack.getItem()).setLevel(stack, 1);
        stack.setDisplayName(new KeybindTextComponent(TextFormatting.YELLOW + "Totem of Health"));
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TotemOfHealthRecipe> {

        @Override
        public TotemOfHealthRecipe read(ResourceLocation recipeId, JsonObject json) {
            final String group = JSONUtils.getString(json, "group", "");
            final Map<String, Ingredient> keys = deserializeKey(JSONUtils.getJsonObject(json, "key"));
            final String[] pattern = shrink(patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
            final int width = pattern[0].length();
            final int height = pattern.length;
            final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, keys, width, height);
            final ItemStack output = deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new TotemOfHealthRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public TotemOfHealthRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final int width = buffer.readVarInt();
            final int height = buffer.readVarInt();
            final String group = buffer.readString(32767);
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (int index = 0; index < ingredients.size(); ++index) {
                ingredients.set(index, Ingredient.read(buffer));
            }

            final ItemStack output = buffer.readItemStack();
            return new TotemOfHealthRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketBuffer buffer, TotemOfHealthRecipe recipe) {
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
