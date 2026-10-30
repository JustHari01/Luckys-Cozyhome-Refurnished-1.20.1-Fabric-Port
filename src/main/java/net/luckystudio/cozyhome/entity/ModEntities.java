package net.luckystudio.cozyhome.entity;

import net.luckystudio.cozyhome.CozyHome;
import net.luckystudio.cozyhome.entity.custom.SeatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<SeatEntity> SEAT_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("cozyhome", "seat"),
            EntityType.Builder.create(SeatEntity::new, SpawnGroup.MISC).setDimensions(1f, 1f).build(CozyHome.MOD_ID + ":seat"));

    public static void registerModEntities() {}
}
