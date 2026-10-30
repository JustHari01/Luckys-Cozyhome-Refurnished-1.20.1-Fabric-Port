package net.luckystudio.cozyhome.util;

/** Stores the custom tint used by lamps, couches, and sofas. */
public interface DyeableBlockEntity {
    int getDyeColor(int defaultColor);

    void setDyeColor(int color);
}
