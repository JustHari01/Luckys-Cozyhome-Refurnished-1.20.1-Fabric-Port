package net.luckystudio.cozyhome.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

public final class BlockEntityTickers {
    private BlockEntityTickers() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity, E extends BlockEntity> @Nullable BlockEntityTicker<T> whenType(
            BlockEntityType<T> actualType,
            BlockEntityType<E> expectedType,
            BlockEntityTicker<E> ticker
    ) {
        return actualType == expectedType ? (BlockEntityTicker<T>) (BlockEntityTicker<?>) ticker : null;
    }
}
