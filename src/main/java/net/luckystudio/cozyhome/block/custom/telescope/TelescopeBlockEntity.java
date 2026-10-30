package net.luckystudio.cozyhome.block.custom.telescope;

import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TelescopeBlockEntity extends BlockEntity {
    public float yaw;
    public float pitch;
    float maxPitchAngle = 60;

    public TelescopeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.TELESCOPE_BLOCK_ENTITY, pos, state); // Pass the correct BlockEntityType here
        this.yaw = 0;
        this.pitch = 45;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        updateListeners();
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = Math.max(-maxPitchAngle, Math.min(maxPitchAngle, pitch));
        updateListeners();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("yaw", this.yaw);
        nbt.putFloat("pitch", this.pitch);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.yaw = nbt.getFloat("yaw");
        this.pitch = nbt.getFloat("pitch");
    }

    // This Syncs the Client and Server
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    // This Syncs the Client and Server
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}
