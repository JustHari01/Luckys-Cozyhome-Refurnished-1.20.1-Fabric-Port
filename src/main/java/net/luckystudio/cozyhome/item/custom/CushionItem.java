package net.luckystudio.cozyhome.item.custom;

import net.minecraft.client.item.TooltipContext;

import net.minecraft.world.World;

import net.luckystudio.cozyhome.util.ModScreenTexts;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CushionItem extends Item {
    public CushionItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("tooltip.cozyhome.applied_when_interacted_with").formatted(Formatting.GRAY));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("blocks.cozyhome.chairs")));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("blocks.cozyhome.sofas")));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("blocks.cozyhome.couches")));
    }
}
