package growthcraft.bees.util;

import growthcraft.api.core.i18n.GrcI18n;
import growthcraft.api.core.util.ITagFormatter;
import growthcraft.core.util.TagFormatterItem;
import growthcraft.core.util.UnitFormatter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class TagFormatterBeeBox implements ITagFormatter
{
	public static final TagFormatterBeeBox INSTANCE = new TagFormatterBeeBox();

	public List<String> format(List<String> list, NBTTagCompound tag)
	{
		list.add(TextFormatting.GRAY + GrcI18n.translate("grc.bees.bonus_prefix") + " " +
			TextFormatting.WHITE + UnitFormatter.booleanAsValue(tag.getBoolean("has_bonus")));

		list.add(TextFormatting.GRAY + GrcI18n.translate("grc.bees.bees_prefix") + " " +
			TagFormatterItem.INSTANCE.formatItem(tag.getCompoundTag("bee")));

		list.add(TextFormatting.GRAY + GrcI18n.translate("grc.bees.honey_prefix") + " " +
			UnitFormatter.fraction(
				"" + TextFormatting.WHITE + tag.getInteger("honeycomb_count"),
				"" + TextFormatting.YELLOW + tag.getInteger("honey_count"),
				"" + TextFormatting.WHITE + tag.getInteger("honeycomb_max")
			)
		);

		if (tag.hasKey("growth_rate"))
		{
			list.add(TextFormatting.GRAY + GrcI18n.translate("grc.bees.growth_rate_prefix") + " " +
				TextFormatting.WHITE + GrcI18n.translate("grc.bees.growth_rate_value", (int)(tag.getFloat("growth_rate") * 100)));
		}

		return list;
	}
}
