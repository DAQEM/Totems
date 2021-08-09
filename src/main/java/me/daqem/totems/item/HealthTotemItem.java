package me.daqem.totems.item;

import me.daqem.totems.Totems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class HealthTotemItem extends Item {

    public HealthTotemItem() {
        super(new Properties().group(Totems.TAB).maxStackSize(1));
    }

    public int getLevel(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Level");
    }

    public void setLevel(ItemStack stack, int itemHandler) {
        stack.getOrCreateTag().putInt("Level", itemHandler);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new KeybindTextComponent(TextFormatting.YELLOW + "Health Totem");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this.getLevel(stack) == 0) {
            this.setLevel(stack, 1);
        }
        tooltip.add(new KeybindTextComponent(TextFormatting.GOLD + "Level: " + TextFormatting.YELLOW + this.getLevel(stack)));
    }
}
