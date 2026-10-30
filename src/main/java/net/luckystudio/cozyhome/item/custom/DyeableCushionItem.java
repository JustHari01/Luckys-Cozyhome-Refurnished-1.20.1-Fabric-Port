package net.luckystudio.cozyhome.item.custom;

import net.minecraft.client.item.TooltipContext;

import net.minecraft.world.World;

import net.luckystudio.cozyhome.util.ModColorHandler;
import net.minecraft.item.ItemStack;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class DyeableCushionItem extends CushionItem {
    public DyeableCushionItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        int color = ModColorHandler.getItemColor(stack, -393218); // Using 'this' as the BlockEntity
        Text colorName = ModColorHandler.getColorName(color);
        return colorName.copy().append(Text.literal(" ")).append(super.getName(stack));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.cozyhome.dyeable").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
