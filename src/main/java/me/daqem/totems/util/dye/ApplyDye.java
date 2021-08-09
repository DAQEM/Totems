package me.daqem.totems.util.dye;

import me.daqem.totems.item.TotemBagItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class ApplyDye {

    public static void applyDyes(ItemStack backpack, Collection<ItemStack> dyes) {
        int[] componentSums = new int[3];
        int maxColorSum = 0;
        int colorCount = 0;

        int backpackColor = TotemBagItem.getTotemBagColor(backpack);
        if (backpackColor != DyeColor.WHITE.getFireworkColor()) {
            float r = (float) (backpackColor >> 16 & 255) / 255.0F;
            float g = (float) (backpackColor >> 8 & 255) / 255.0F;
            float b = (float) (backpackColor & 255) / 255.0F;
            maxColorSum = (int) ((float) maxColorSum + Math.max(r, Math.max(g, b)) * 255.0F);
            componentSums[0] = (int) ((float) componentSums[0] + r * 255.0F);
            componentSums[1] = (int) ((float) componentSums[1] + g * 255.0F);
            componentSums[2] = (int) ((float) componentSums[2] + b * 255.0F);
            ++colorCount;
        }

        for (ItemStack dye : dyes) {
            DyeColor dyeColor = DyeColor.getColor(dye);
            if (dyeColor != null) {
                float[] componentValues = dyeColor.getColorComponentValues();
                int r = (int) (componentValues[0] * 255.0F);
                int g = (int) (componentValues[1] * 255.0F);
                int b = (int) (componentValues[2] * 255.0F);
                maxColorSum += Math.max(r, Math.max(g, b));
                componentSums[0] += r;
                componentSums[1] += g;
                componentSums[2] += b;
                ++colorCount;
            }
        }

        if (colorCount > 0) {
            int r = componentSums[0] / colorCount;
            int g = componentSums[1] / colorCount;
            int b = componentSums[2] / colorCount;
            float maxAverage = (float) maxColorSum / (float) colorCount;
            float max = (float) Math.max(r, Math.max(g, b));
            r = (int) ((float) r * maxAverage / max);
            g = (int) ((float) g * maxAverage / max);
            b = (int) ((float) b * maxAverage / max);
            int finalColor = (r << 8) + g;
            finalColor = (finalColor << 8) + b;
            TotemBagItem.setTotemBagColor(backpack, finalColor);
        }
    }
}
