package me.daqem.totems.crafting.recipe;

import com.google.gson.JsonObject;
import me.daqem.totems.Totems;
import me.daqem.totems.init.ModItems;
import me.daqem.totems.util.dye.ApplyDye;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class TotemBagRecipe extends ShapedRecipe {

    public static final ResourceLocation NAME = Totems.getId("totem_bag");
    public static final Serializer SERIALIZER = new Serializer();

    public TotemBagRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
        super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int gemCount = 0;
        int woolCount = 0;
        int stringCount = 0;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.totemGem) {
                    ++gemCount;
                } else if (stack.getItem().isIn(Objects.requireNonNull(ItemTags.getCollection().get(Totems.getId("wool"))))) {
                    ++woolCount;
                } else if (stack.getItem() == Items.STRING) {
                    ++stringCount;
                } else {
                    return false;
                }
            }
        }
        return gemCount == 1 && woolCount == 4 && stringCount == 4;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        Collection<ItemStack> dyes = new ArrayList<>();
        ItemStack result = new ItemStack(ModItems.totemBag);
        dyes.add(new ItemStack(Items.BROWN_DYE));
        dyes.add(new ItemStack(Items.BROWN_DYE));
        ApplyDye.applyDyes(result, dyes);
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TotemBagRecipe> {

        @Override
        public TotemBagRecipe read(ResourceLocation recipeId, JsonObject json) {
            final String group = JSONUtils.getString(json, "group", "");
            final Map<String, Ingredient> keys = deserializeKey(JSONUtils.getJsonObject(json, "key"));
            final String[] pattern = shrink(patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
            final int width = pattern[0].length();
            final int height = pattern.length;
            final NonNullList<Ingredient> ingredients = deserializeIngredients(pattern, keys, width, height);
            final ItemStack output = deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new TotemBagRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public TotemBagRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final int width = buffer.readVarInt();
            final int height = buffer.readVarInt();
            final String group = buffer.readString(32767);
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (int index = 0; index < ingredients.size(); ++index) {
                ingredients.set(index, Ingredient.read(buffer));
            }

            final ItemStack output = buffer.readItemStack();
            return new TotemBagRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketBuffer buffer, TotemBagRecipe recipe) {
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
