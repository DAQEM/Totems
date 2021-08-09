package me.daqem.totems.inventory;

import me.daqem.totems.Totems;
import me.daqem.totems.init.ModContainerTypes;
import me.daqem.totems.item.TotemBagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TotemBagContainer extends Container {

    private final ItemStack item;
    private final IItemHandler itemHandler;
    private int blocked = -1;

    public TotemBagContainer(int id, PlayerInventory playerInventory) {
        super(ModContainerTypes.totem_bag, id);
        this.item = getHeldItem(playerInventory.player);
        this.itemHandler = ((TotemBagItem)this.item.getItem()).getInventory(this.item);



        // Add backpack slots (3 rows of 9)
        for (int i = 0; i < this.itemHandler.getSlots(); ++i) {
            int x = 8 + 18 * (i % 9);
            int y = 18 + 18 * (i / 9);
            addSlot(new SlotItemHandler(this.itemHandler, i, x, y) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return Objects.requireNonNull(ItemTags.getCollection().get(Totems.getId("totems"))).contains(stack.getItem());
                }
            });

        }

        final int rowCount = this.itemHandler.getSlots() / 9;
        final int yOffset = (rowCount - 4) * 18;

        // Player inventory
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + yOffset));
            }
        }

        // Hotbar
        for (int x = 0; x < 9; ++x) {
            Slot slot = addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + yOffset) {
                @Override
                public boolean canTakeStack(PlayerEntity playerIn) {
                    return slotNumber != blocked;
                }
            });

            if (x == playerInventory.currentItem && ItemStack.areItemStacksEqual(playerInventory.getCurrentItem(), this.item)) {
                blocked = slot.slotNumber;
            }
        }
    }

    private static ItemStack getHeldItem(PlayerEntity player) {
        if (player.getHeldItemMainhand().getItem() instanceof TotemBagItem)
            return player.getHeldItemMainhand();
        if (player.getHeldItemOffhand().getItem() instanceof TotemBagItem)
            return player.getHeldItemOffhand();
        return ItemStack.EMPTY;
    }

    public int getInventoryRows() {
        return this.itemHandler.getSlots() / 9;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        // Save the inventory to the backpack's NBT
        ((TotemBagItem) this.item.getItem()).saveInventory(this.item, this.itemHandler);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        // This method handles shift-clicking to transfer items quickly. This can easily crash the game if not coded
        // correctly. The first slots (index 0 to whatever) are usually the inventory block/item, while player slots
        // start after those.
        Slot slot = this.getSlot(index);

        if (!slot.canTakeStack(playerIn)) {
            return slot.getStack();
        }

        if (index == blocked || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        int containerSlots = itemHandler.getSlots();
        if (index < containerSlots) {
            if (!this.mergeItemStack(stack, containerSlots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChanged();
        } else if (!this.mergeItemStack(stack, 0, containerSlots, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        return slot.onTake(playerIn, newStack);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (slotId < 0 || slotId > inventorySlots.size()) {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }

        Slot slot = inventorySlots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn)) {
            return slot.getStack();
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    private static boolean isTotemBag(ItemStack stack) {
        return stack.getItem() instanceof TotemBagItem;
    }

    private boolean canTake(int slotId, Slot slot, int button, PlayerEntity player, ClickType clickType) {
        if (slotId == blocked || slotId <= itemHandler.getSlots() - 1 && isTotemBag(player.inventory.getItemStack())) {
            return false;
        }

        // Hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // Block swapping with container
            if (blocked == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return !isTotemBag(slot.getStack()) && !isTotemBag(hotbarSlot.getStack());
            }
        }

        return true;
    }
}
