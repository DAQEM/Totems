package me.daqem.totems.item;

import me.daqem.totems.Totems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class TotemItem extends Item {

    public TotemItem() {
        super(new Properties().group(Totems.TAB).maxStackSize(1));
    }

    public int getLevel(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Level");
    }

    public void setLevel(ItemStack stack, int level) {
        stack.getOrCreateTag().putInt("Level", level);
    }

    public int getMaxLevel(ItemStack stack) {
        return isTotemOfFlying(stack) ? 3 : 10;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new KeybindTextComponent(TextFormatting.YELLOW + stack.getItem().getName().getString());
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return this.getLevel(stack) == this.getMaxLevel(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this.getLevel(stack) < 1) this.setLevel(stack, 1);
        if (this.getLevel(stack) > this.getMaxLevel(stack)) this.setLevel(stack, this.getMaxLevel(stack));
        tooltip.add(new KeybindTextComponent(TextFormatting.GOLD + "Level: " + TextFormatting.YELLOW + this.getLevel(stack)));
    }

    public static boolean isTotemOfFlying(ItemStack stack) {
        return Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation(Totems.MODID, "totem_of_flight"));
    }
}
