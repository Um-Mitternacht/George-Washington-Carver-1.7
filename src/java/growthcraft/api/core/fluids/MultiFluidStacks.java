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
package growthcraft.api.core.fluids;

import growthcraft.api.core.definition.IMultiFluidStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiFluidStacks implements IMultiFluidStacks
{
	private List<FluidStack> fluidStacks;
	private transient List<ItemStack> fluidContainers;

	public MultiFluidStacks(@Nonnull List<FluidStack> stacks)
	{
		this.fluidStacks = stacks;
	}

	public MultiFluidStacks(@Nonnull FluidStack... stacks)
	{
		this(Arrays.asList(stacks));
	}

	public List<String> getNames()
	{
		final List<String> result = new ArrayList<String>();
		for (FluidStack stack : fluidStacks)
		{
			final Fluid fluid = stack.getFluid();
			if (fluid != null)
			{
				result.add(fluid.getName());
			}
		}
		return result;
	}

	@Override
	public int getAmount()
	{
		for (FluidStack stack : fluidStacks)
		{
			return stack.amount;
		}
		return 0;
	}

	@Override
	public List<FluidStack> getFluidStacks()
	{
		return fluidStacks;
	}

	@Override
	public boolean containsFluid(@Nullable Fluid expectedFluid)
	{
		if (FluidTest.isValid(expectedFluid))
		{
			for (FluidStack content : getFluidStacks())
			{
				if (content.getFluid() == expectedFluid) return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsFluidStack(@Nullable FluidStack stack)
	{
		if (!FluidTest.isValid(stack)) return false;
		for (FluidStack content : getFluidStacks())
		{
			if (content.isFluidEqual(stack)) return true;
		}
		return false;
	}

	@Override
	public List<ItemStack> getItemStacks()
	{
		if (fluidContainers == null)
		{
			fluidContainers = FluidUtils.getFluidContainers(getFluidStacks());
		}

		return fluidContainers;
	}
}
