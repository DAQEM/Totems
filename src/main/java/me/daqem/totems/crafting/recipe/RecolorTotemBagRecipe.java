package me.daqem.totems.crafting.recipe;

import com.google.gson.JsonObject;
import me.daqem.totems.Totems;
import me.daqem.totems.item.TotemBagItem;
import me.daqem.totems.util.dye.ApplyDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class RecolorTotemBagRecipe implements IRecipe<TotemBagItem> {

    public static final ResourceLocation NAME = Totems.getId("recolor_backpack");
    public static final Serializer SERIALIZER = new Serializer();


//    @Override
//    public boolean matches(CraftingInventory inv, World worldIn) {
//        int totemBagCount = 0;
//        int dyeCount = 0;
//
//        for(int i = 0; i < inv.getSizeInventory(); ++i) {
//            ItemStack stack = inv.getStackInSlot(i);
//            if (!stack.isEmpty()) {
//                if (stack.getItem() instanceof TotemBagItem) {
//                    ++totemBagCount;
//                } else if (stack.getItem().isIn(Tags.Items.DYES)) {
//                    ++dyeCount;
//                } else {
//                    return false;
//                }
//            }
//        }
//        return totemBagCount == 1 && dyeCount >= 1 && dyeCount <= 8;
//    }
//
//    @Override
//    public ItemStack getCraftingResult(CraftingInventory inv) {
//        ItemStack totemBag = ItemStack.EMPTY;
//        Collection<ItemStack> dyes = new ArrayList<>();
//        for(int i = 0; i < inv.getSizeInventory(); ++i) {
//            ItemStack stack = inv.getStackInSlot(i);
//            if (!stack.isEmpty()) {
//                if (stack.getItem() instanceof TotemBagItem) {
//                    totemBag = stack;
//                } else if (stack.getItem().isIn(Tags.Items.DYES)) {
//                    dyes.add(stack);
//                }
//            }
//        }
//        ItemStack result = totemBag.copy();
//        ApplyDye.applyDyes(result, dyes);
//        return result;
//    }

    @Override
    public boolean matches(TotemBagItem inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(TotemBagItem inv) {
        ItemStack totemBag = ItemStack.EMPTY;
        Collection<ItemStack> dyes = new ArrayList<>();
        for(int i = 0; i < inv.getInventorySize(); ++i) {
            ItemStack stack = inv.getInventory(totemBag).getStackInSlot(i);
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
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecolorTotemBagRecipe> {

        @Override
        public RecolorTotemBagRecipe read(ResourceLocation recipeId, JsonObject json) {
            return new RecolorTotemBagRecipe();
        }

        @Nullable
        @Override
        public RecolorTotemBagRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new RecolorTotemBagRecipe();
        }

        @Override
        public void write(PacketBuffer buffer, RecolorTotemBagRecipe recipe) {
        }
    }
}
