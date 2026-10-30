package net.luckystudio.cozyhome.block.custom.clocks.grandfather_clock;

import net.luckystudio.cozyhome.block.custom.clocks.ClockFunctionalityHandler;
import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.luckystudio.cozyhome.block.util.interfaces.ClockBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GrandfatherClockBlockEntity extends BlockEntity implements ClockBlock {
    public float lastHourHandAngle = 0.0f;
    public float currentHourHandAngle = 0.0f;
    public float lastMinuteHandAngle = 0.0f;
    public float currentMinuteHandAngle = 0.0f;
    public float lastPendulumAngle = 0.0f;
    public float currentPendulumAngle = 0.0f;
    public float hourHandSpeed = 0.0f;

    int ticks = 0;

    public GrandfatherClockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GRANDFATHER_CLOCK_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GrandfatherClockBlockEntity blockEntity) {
        blockEntity.incrementTicks();
        ClockFunctionalityHandler.handleHandRotations(world, pos, state, blockEntity);
        ClockFunctionalityHandler.handleGrandfatherClock(world, pos, state, blockEntity, 7.5f);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("ticks", this.ticks);
        nbt.putFloat("currentHourHandAngle", this.currentHourHandAngle);
        nbt.putFloat("currentMinuteHandAngle", this.currentMinuteHandAngle);
        nbt.putFloat("currentPendulumAngle", this.currentPendulumAngle);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.ticks = nbt.getInt("ticks");
        this.currentHourHandAngle = nbt.getFloat("currentHourHandAngle");
        this.currentMinuteHandAngle = nbt.getFloat("currentMinuteHandAngle");
        this.currentPendulumAngle = nbt.getFloat("currentPendulumAngle");
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

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public void incrementTicks() {
        ticks++;
    }

    @Override
    public float getCurrentHourHandAngle() {
        return currentHourHandAngle;
    }

    @Override
    public void setCurrentHourHandAngle(float angle) {
        currentHourHandAngle = angle;
    }

    @Override
    public float getLastHourHandAngle() {
        return lastHourHandAngle;
    }

    @Override
    public void setLastHourHandAngle(float angle) {
        lastHourHandAngle = angle;
    }

    @Override
    public float getCurrentMinuteHandAngle() {
        return currentMinuteHandAngle;
    }

    @Override
    public void setCurrentMinuteHandAngle(float angle) {
        currentMinuteHandAngle = angle;
    }

    @Override
    public float getLastMinuteHandAngle() {
        return lastMinuteHandAngle;
    }

    @Override
    public void setLastMinuteHandAngle(float angle) {
        lastMinuteHandAngle = angle;
    }

    @Override
    public float getCurrentPendulumAngle() {
        return currentPendulumAngle;
    }

    @Override
    public void setCurrentPendulumAngle(float angle) {
        currentPendulumAngle = angle;
    }

    @Override
    public float getLastPendulumAngle() {
        return lastPendulumAngle;
    }

    @Override
    public void setLastPendulumAngle(float angle) {
        lastPendulumAngle = angle;
    }

    @Override
    public World getWorld() {
        return super.getWorld();
    }
}
