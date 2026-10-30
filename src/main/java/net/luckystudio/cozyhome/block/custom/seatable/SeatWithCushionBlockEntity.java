package net.luckystudio.cozyhome.block.custom.seatable;

import net.luckystudio.cozyhome.item.custom.CushionItem;
import net.luckystudio.cozyhome.util.DyeableBlockEntity;
import net.luckystudio.cozyhome.util.ModColorHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class SeatWithCushionBlockEntity extends BlockEntity implements Inventory, SidedInventory, DyeableBlockEntity {
    private static final int[] NO_AUTOMATION_SLOTS = new int[0];
    private ItemStack stack;
    private int dyeColor;
    private boolean hasDyeColor;
    public float currentOffset;

    public SeatWithCushionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.stack = ItemStack.EMPTY;
        this.currentOffset = 0;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.stack.isEmpty()) {
            nbt.put("item", this.stack.writeNbt(new NbtCompound()));
        }
        if (this.hasDyeColor) {
            ModColorHandler.writeBlockColor(nbt, this.dyeColor);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("item", NbtElement.COMPOUND_TYPE)) {
            this.stack = ItemStack.fromNbt(nbt.getCompound("item"));
        } else {
            this.stack = ItemStack.EMPTY;
        }
        this.hasDyeColor = ModColorHandler.hasBlockColor(nbt);
        this.dyeColor = ModColorHandler.readBlockColor(nbt, 0);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void setDyeColor(int color) {
        this.dyeColor = color;
        this.hasDyeColor = true;
        this.markDirty();
    }

    @Override
    public int getDyeColor(int defaultColor) {
        return this.hasDyeColor ? this.dyeColor : defaultColor;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        if (this.stack.getCount() > this.getMaxCountPerStack()) {
            this.stack.setCount(this.getMaxCountPerStack());
        }
        this.updateListeners();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot == 0 ? this.getStack() : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (slot != 0 || this.stack.isEmpty() || amount <= 0) {
            return ItemStack.EMPTY;
        }
        ItemStack removed = this.stack.split(amount);
        if (this.stack.isEmpty()) {
            this.stack = ItemStack.EMPTY;
        }
        this.updateListeners();
        return removed;
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        ItemStack removed = this.stack;
        this.stack = ItemStack.EMPTY;
        this.updateListeners();
        return removed;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0) {
            this.setStack(stack);
        }
    }

    @Override
    public void clear() {
        this.setStack(ItemStack.EMPTY);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        // Keep cushion insertion controlled by the block's hand interaction; hoppers must not insert items.
        return false;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.world != null && this.world.getBlockEntity(this.pos) == this;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return NO_AUTOMATION_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    private void updateListeners() {
        this.markDirty();
        if (this.world != null) {
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }
}
