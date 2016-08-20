/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package growthcraft.cellar.common.item;

import growthcraft.api.core.definition.IItemStackFactory;
import growthcraft.cellar.GrowthCraftCellar;

import net.minecraft.item.ItemStack;

public enum EnumYeast implements IItemStackFactory
{
	BREWERS,
	LAGER,
	BAYANUS,
	ETHEREAL,
	ORIGIN;

	public static final int length = values().length;

	/**
	 * Convience method for creating the corresponding yeast stack
	 *   example: EnumYeast.BREWERS.asStack(size)
	 *
	 * @param size - size of the stack to create
	 * @return yeast stack
	 */
	public ItemStack asStack(int size)
	{
		return GrowthCraftCellar.items.yeast.asStack(size, ordinal());
	}

	/**
	 * @return yeast stack, size: 1
	 */
	public ItemStack asStack()
	{
		return asStack(1);
	}
}
