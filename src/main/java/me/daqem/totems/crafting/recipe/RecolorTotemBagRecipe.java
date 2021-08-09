package me.daqem.totems.crafting.recipe;

import com.google.gson.JsonObject;
import me.daqem.totems.item.TotemBagItem;
import me.daqem.totems.util.dye.ApplyDye;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class RecolorTotemBagRecipe extends SpecialRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public RecolorTotemBagRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int totemBagCount = 0;
        int dyeCount = 0;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof TotemBagItem) {
                    ++totemBagCount;
                } else if (stack.getItem().isIn(Tags.Items.DYES)) {
                    ++dyeCount;
                } else {
                    return false;
                }
            }
        }
        return totemBagCount == 1 && dyeCount >= 1 && dyeCount <= 8;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack totemBag = ItemStack.EMPTY;
        Collection<ItemStack> dyes = new ArrayList<>();
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof TotemBagItem) {
                    totemBag = stack;
                } else if (stack.getItem().isIn(Tags.Items.DYES)) {
                    dyes.add(stack);
                }
            }
        }
        ItemStack result = totemBag.copy();
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecolorTotemBagRecipe> {

        @Override
        public RecolorTotemBagRecipe read(ResourceLocation recipeId, JsonObject json) {
            return new RecolorTotemBagRecipe(recipeId);
        }

        @Nullable
        @Override
        public RecolorTotemBagRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new RecolorTotemBagRecipe(recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, RecolorTotemBagRecipe recipe) {
        }
    }
}
