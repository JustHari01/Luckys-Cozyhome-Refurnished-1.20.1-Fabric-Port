package net.luckystudio.cozyhome.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModColorHandler {
    private static final String DISPLAY_TAG = "display";
    private static final String ITEM_COLOR_TAG = "color";
    private static final String BLOCK_COLOR_TAG = "DyeColor";

    public static final Map<Integer, Text> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put(-393218, Text.translatable("cozyhome.color.white"));
        COLOR_MAP.put(-6447721, Text.translatable("cozyhome.color.gray"));
        COLOR_MAP.put(-14869215, Text.translatable("cozyhome.color.black"));
        COLOR_MAP.put(-8170446, Text.translatable("cozyhome.color.brown"));
        COLOR_MAP.put(-5231066, Text.translatable("cozyhome.color.red"));
        COLOR_MAP.put(-425955, Text.translatable("cozyhome.color.orange"));
        COLOR_MAP.put(-75715, Text.translatable("cozyhome.color.yellow"));
        COLOR_MAP.put(-8337633, Text.translatable("cozyhome.color.lime"));
        COLOR_MAP.put(-10585066, Text.translatable("cozyhome.color.green"));
        COLOR_MAP.put(-15295332, Text.translatable("cozyhome.color.cyan"));
        COLOR_MAP.put(-12930086, Text.translatable("cozyhome.color.light_blue"));
        COLOR_MAP.put(-12827478, Text.translatable("cozyhome.color.blue"));
        COLOR_MAP.put(-7785800, Text.translatable("cozyhome.color.purple"));
        COLOR_MAP.put(-3715395, Text.translatable("cozyhome.color.magenta"));
        COLOR_MAP.put(-816214, Text.translatable("cozyhome.color.pink"));
    }

    public static Text getColorName(int color) {
        return COLOR_MAP.getOrDefault(color, Text.translatable("cozyhome.custom_colored"));
    }

    public static int getItemColor(ItemStack stack, int defaultColor) {
        NbtCompound display = stack.getSubNbt(DISPLAY_TAG);
        return display != null && display.contains(ITEM_COLOR_TAG)
                ? (display.getInt(ITEM_COLOR_TAG) | 0xFF000000)
                : defaultColor;
    }

    public static boolean hasItemColor(ItemStack stack) {
        NbtCompound display = stack.getSubNbt(DISPLAY_TAG);
        return display != null && display.contains(ITEM_COLOR_TAG);
    }

    public static void setItemColor(ItemStack stack, int color) {
        stack.getOrCreateSubNbt(DISPLAY_TAG).putInt(ITEM_COLOR_TAG, color & 0x00FFFFFF);
    }

    public static int getBlockColor(@Nullable BlockEntity entity, int defaultColor) {
        if (entity instanceof DyeableBlockEntity dyeable) {
            return dyeable.getDyeColor(defaultColor) | 0xFF000000;
        }
        return defaultColor;
    }

    public static void writeBlockColor(NbtCompound nbt, int color) {
        nbt.putInt(BLOCK_COLOR_TAG, color);
    }

    public static boolean hasBlockColor(NbtCompound nbt) {
        return nbt.contains(BLOCK_COLOR_TAG);
    }

    public static int readBlockColor(NbtCompound nbt, int defaultColor) {
        return nbt.contains(BLOCK_COLOR_TAG) ? nbt.getInt(BLOCK_COLOR_TAG) : defaultColor;
    }

    public static int averageColor(int first, int second) {
        first |= 0xFF000000;
        second |= 0xFF000000;
        int red = (((first >> 16) & 0xFF) + ((second >> 16) & 0xFF)) / 2;
        int green = (((first >> 8) & 0xFF) + ((second >> 8) & 0xFF)) / 2;
        int blue = ((first & 0xFF) + (second & 0xFF)) / 2;
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    public static float red(int color) { return ((color >>> 16) & 0xFF) / 255.0F; }
    public static float green(int color) { return ((color >>> 8) & 0xFF) / 255.0F; }
    public static float blue(int color) { return (color & 0xFF) / 255.0F; }
}
