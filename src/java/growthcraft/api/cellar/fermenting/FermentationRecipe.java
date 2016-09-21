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
package growthcraft.api.cellar.fermenting;

import growthcraft.api.core.definition.IMultiFluidStacks;
import growthcraft.api.core.definition.IMultiItemStacks;
import growthcraft.api.core.fluids.FluidTest;
import growthcraft.api.core.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FermentationRecipe implements IFermentationRecipe
{
	private final IMultiItemStacks fermentingItem;
	private final IMultiFluidStacks inputFluidStack;
	private final FluidStack outputFluidStack;
	private final int time;

	public FermentationRecipe(@Nonnull IMultiFluidStacks pInputFluidStack, @Nonnull IMultiItemStacks pFermentingItem, @Nonnull FluidStack pOutputFluidStack, int pTime)
	{
		this.fermentingItem = pFermentingItem;
		this.inputFluidStack = pInputFluidStack;
		this.outputFluidStack = pOutputFluidStack;
		this.time = pTime;
	}

	@Override
	public IMultiFluidStacks getInputFluidStack()
	{
		return inputFluidStack;
	}

	@Override
	public FluidStack getOutputFluidStack()
	{
		return outputFluidStack;
	}

	@Override
	public IMultiItemStacks getFermentingItemStack()
	{
		return fermentingItem;
	}

	@Override
	public int getTime()
	{
		return time;
	}

	@Override
	public boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack)
	{
		if (FluidTest.isValid(fluidStack) && ItemTest.isValid(itemStack))
		{
			if (FluidTest.hasEnough(inputFluidStack, fluidStack))
			{
				return ItemTest.hasEnough(fermentingItem, itemStack);
			}
		}
		return false;
	}

	@Override
	public boolean matchesIngredient(@Nullable FluidStack fluidStack)
	{
		return FluidTest.fluidMatches(inputFluidStack, fluidStack);
	}

	@Override
	public boolean matchesIngredient(@Nullable ItemStack stack)
	{
		return ItemTest.itemMatches(fermentingItem, stack);
	}
}
