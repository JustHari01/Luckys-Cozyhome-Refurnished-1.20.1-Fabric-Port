package net.luckystudio.cozyhome.block.custom.lamps;

import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.luckystudio.cozyhome.util.DyeableBlockEntity;
import net.luckystudio.cozyhome.util.ModColorHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LampBlockEntity extends BlockEntity implements DyeableBlockEntity {
    public float currentOffset;
    private int dyeColor;
    private boolean hasDyeColor;

    public LampBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.LAMP_BLOCK_ENTITY, pos, state);
        this.currentOffset = 0;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.hasDyeColor) {
            ModColorHandler.writeBlockColor(nbt, this.dyeColor);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.hasDyeColor = ModColorHandler.hasBlockColor(nbt);
        this.dyeColor = ModColorHandler.readBlockColor(nbt, 0);
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
    public Object getRenderData() {
        return this.getDyeColor(-17170434);
    }
}
