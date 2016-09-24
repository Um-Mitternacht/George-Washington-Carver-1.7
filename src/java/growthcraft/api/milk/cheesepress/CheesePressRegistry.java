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
package growthcraft.api.milk.cheesepress;

import growthcraft.api.core.item.ItemTest;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.log.NullLogger;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CheesePressRegistry implements ICheesePressRegistry
{
	private ILogger logger = NullLogger.INSTANCE;
	private List<ICheesePressRecipe> recipes = new ArrayList<ICheesePressRecipe>();

	@Override
	public void setLogger(@Nonnull ILogger l)
	{
		this.logger = l;
	}

	@Override
	public void addRecipe(@Nonnull ICheesePressRecipe recipe)
	{
		logger.debug("Adding new cheese press recipe {%s}", recipe);
		recipes.add(recipe);
	}

	@Override
	public void addRecipe(@Nonnull ItemStack stack, @Nonnull ItemStack output, int time)
	{
		addRecipe(new CheesePressRecipe(stack, output, time));
	}

	@Override
	public ICheesePressRecipe findRecipe(@Nullable ItemStack stack)
	{
		if (ItemTest.isValid(stack))
		{
			for (ICheesePressRecipe recipe : recipes)
			{
				if (recipe.isMatchingRecipe(stack)) return recipe;
			}
		}
		return null;
	}
}
