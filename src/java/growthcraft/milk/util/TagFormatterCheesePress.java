/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.milk.util;

import growthcraft.api.core.i18n.GrcI18n;
import growthcraft.api.core.util.ITagFormatter;
import growthcraft.core.util.TagFormatterItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class TagFormatterCheesePress implements ITagFormatter
{
	public static final TagFormatterCheesePress INSTANCE = new TagFormatterCheesePress();

	public List<String> format(List<String> list, NBTTagCompound nbt)
	{
		list.add(TextFormatting.GRAY + GrcI18n.translate("grcmilk.cheese_press.pressing.state.prefix") + " " +
				TextFormatting.WHITE + GrcI18n.translate("grcmilk.cheese_press.pressing.state." + nbt.getBoolean("pressed")));
		list.add(TextFormatting.GRAY +
			GrcI18n.translate(
				"grcmilk.cheese_press.itemslot.item",
				TagFormatterItem.INSTANCE.formatItem(nbt.getCompoundTag("item"))
			)
		);
		return list;
	}
}
