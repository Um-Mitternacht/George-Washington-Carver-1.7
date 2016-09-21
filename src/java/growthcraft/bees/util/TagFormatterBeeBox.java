package growthcraft.bees.util;

import growthcraft.api.core.i18n.GrcI18n;
import growthcraft.api.core.util.ITagFormatter;
import growthcraft.core.util.TagFormatterItem;
import growthcraft.core.util.UnitFormatter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class TagFormatterBeeBox implements ITagFormatter
{
	public static final TagFormatterBeeBox INSTANCE = new TagFormatterBeeBox();

	public List<String> format(List<String> list, NBTTagCompound tag)
	{
		list.add(EnumChatFormatting.GRAY + GrcI18n.translate("grc.bees.bonus_prefix") + " " +
			EnumChatFormatting.WHITE + UnitFormatter.booleanAsValue(tag.getBoolean("has_bonus")));

		list.add(EnumChatFormatting.GRAY + GrcI18n.translate("grc.bees.bees_prefix") + " " +
			TagFormatterItem.INSTANCE.formatItem(tag.getCompoundTag("bee")));

		list.add(EnumChatFormatting.GRAY + GrcI18n.translate("grc.bees.honey_prefix") + " " +
			UnitFormatter.fraction(
				"" + EnumChatFormatting.WHITE + tag.getInteger("honeycomb_count"),
				"" + EnumChatFormatting.YELLOW + tag.getInteger("honey_count"),
				"" + EnumChatFormatting.WHITE + tag.getInteger("honeycomb_max")
			)
		);

		if (tag.hasKey("growth_rate"))
		{
			list.add(EnumChatFormatting.GRAY + GrcI18n.translate("grc.bees.growth_rate_prefix") + " " +
				EnumChatFormatting.WHITE + GrcI18n.translate("grc.bees.growth_rate_value", (int)(tag.getFloat("growth_rate") * 100)));
		}

		return list;
	}
}
