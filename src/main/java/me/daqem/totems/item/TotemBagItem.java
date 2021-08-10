package me.daqem.totems.item;

import me.daqem.totems.Totems;
import me.daqem.totems.inventory.TotemBagContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TotemBagItem extends Item {
    private static final String NBT_COLOR = "TotemBagColor";

    public TotemBagItem() {
        super(new Properties().group(Totems.TAB).maxStackSize(1));
    }

    public int getInventorySize(ItemStack stack) {
        return 9;
    }

    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }

    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if (itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler) itemHandler).serializeNBT());
        }
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
        return !(player.openContainer instanceof TotemBagContainer);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            playerIn.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, player) -> new TotemBagContainer(id, playerInventory),
                    new TranslationTextComponent("container.totems.totem_bag")
            ));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public static int getTotemBagColor(ItemStack stack) {
        return stack.getOrCreateTag().getInt(NBT_COLOR);
    }

    public static void setTotemBagColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt(NBT_COLOR, color);
    }

    public static int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            return getTotemBagColor(stack);
        }
        return 0xFFFFFF;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            for (DyeColor color : DyeColor.values()) {
                ItemStack stack = new ItemStack(this);
                setTotemBagColor(stack, color.getFireworkColor());
                items.add(stack);
            }
        }
    }
}
